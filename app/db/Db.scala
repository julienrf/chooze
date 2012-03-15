package db

/**
 * Business logic *and* persistence
 */
object Db {
  
  import play.api.db.DB
  import org.scalaquery.session.{Session, Database}
  import play.api.Play.current
  import org.scalaquery.ql.TypeMapper._
  import org.scalaquery.ql._
  import Schema._
  import java.sql.Timestamp
  import java.util.Date

  val driver = if (play.Play.isDev()) extended.H2Driver.Implicit else extended.PostgresDriver.Implicit
  import driver._
  
  val db = Database.forDataSource(DB.getDataSource())
  
  def lastInsertedId(implicit s: Session) = Query(SimpleFunction.nullary[Long]("scope_identity")).firstOption
  
  object Poll {

    // FIXME Use Validation instead of Option? We definitely have two distinct kinds of errors: persistence and logic errors. I’d like to not mix them.
    def create(name: String, slug: String, description: String, alternatives: Seq[String]): Option[Long] = db withSession { implicit s: Session =>
      if (alternatives.size < 2) {
        None
      } else {
        Polls.noId.insert(name, slug, description)
        val maybePollId = lastInsertedId
        for {
          pollId <- maybePollId
          alternative <- alternatives
        } {
          Alternative.create(alternative, pollId)
        }
        maybePollId
      }
    }
    
    def find(slug: String): Option[models.Poll] = db withSession { implicit s: Session =>
      val rows = (for {
        poll <- Polls if poll.slug === slug
        alternative <- Alternatives if alternative.pollId === poll.id
      } yield {
        poll.id ~ poll.name ~ poll.slug ~ poll.description ~ alternative.id ~ alternative.name
      }).list map {
        case (pId, pName, pSlug, pDescr, aId, aName) => ((pId, pName, pSlug, pDescr), (aId, aName))
      }
      rows.groupBy(_._1).mapValues { r => r.map(_._2) }.headOption map { case (p, as) =>
        val alternatives = as map { a => models.Alternative(a._1, a._2) }
        val rows = (for {
          vote <- Votes if vote.pollId === p._1
          note <- Notes if note.voteId === vote.id
        } yield vote.id ~ vote.user ~ note.id ~ note.voteId ~ note.value ~ note.alternativeId).list map {
          case (vId, vUser, nId, nVoteId, nValue, nAltId) => ((vId, vUser), (nId, nVoteId, nValue, nAltId))
        }
        val votes = rows.groupBy(_._1).mapValues { r => r.map (_._2) }.map { case (v, ns) =>
          val notes = ns map { n => models.Note(n._1, alternatives.find(_.id == n._4).get, n._3) }
          models.Vote(v._1, v._2, notes)
        }.toSeq
        models.Poll(p._1, p._2, p._3, p._4, alternatives, votes)
      }
    }

    /**
     * List all Polls slugs of the application
     */
    def slugs: Seq[String] = db withSession { implicit s: Session =>
      (for (poll <- Polls) yield poll.slug).list
    }

    /**
     * Find a slug from a Poll id
     */
    def slug(id: Long): Option[String] = db withSession { implicit s: Session =>
      (for (poll <- Polls if poll.id === id) yield poll.slug).firstOption
    }
  }
  
  object Alternative {
    def create(name: String, pollId: Long): Option[Long] = db withSession { implicit s: Session =>
      Alternatives.noId.insert(name, pollId)
      lastInsertedId
    }

    def findAll(pollId: Long): Option[Seq[models.Alternative]] = db withSession { implicit s: Session =>
      (for { poll <- Polls if poll.id === pollId } yield poll.id).firstOption map { _ =>
        (for {
          poll <- Polls if poll.id === pollId
          alternative <- Alternatives if alternative.pollId === poll.id
        } yield alternative.*).list map {
          case (id, name, _) => models.Alternative(id, name)
        }
      }
    }

  }
  
  object Vote {
    /**
     * Register a vote and update the last modified time of the corresponding poll
     */
    // TODO Check that referenced alternatives do exist and belong to the poll
    def create(pollId: Long, user: String, notes: Seq[(Long, Int)]): Option[Long] = db withSession { implicit s: Session =>
      Votes.noId.insert(user, pollId)
      val maybeVoteId = lastInsertedId
      
      for {
        voteId <- maybeVoteId
        (alternativeId, note) <- notes
      } {
        Note.create(voteId, note, alternativeId)
      }
      
      maybeVoteId
    }
  }
  
  object Note {
    def create(voteId: Long, note: Int, alternativeId: Long): Option[Long] = db withSession { implicit s: Session =>
      Notes.noId.insert(voteId, note, alternativeId)
      lastInsertedId
    }
  }
}