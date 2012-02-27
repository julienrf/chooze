package db

object Db {
  
  import play.api.db.DB
  import org.scalaquery.session.{Session, Database}
  import play.api.Play.current
  import org.scalaquery.ql.extended.H2Driver.Implicit._
  import org.scalaquery.ql.TypeMapper._
  import org.scalaquery.ql._
  import Schema._
  
  val db = Database.forDataSource(DB.getDataSource())
  
  def lastInsertedId(implicit s: Session) = Query(SimpleFunction.nullary[Long]("scope_identity")).firstOption
  
  object Poll {
    def create(name: String, slug: String, description: String, alternatives: Seq[String]): Option[Long] = db withSession { implicit s: Session =>
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
    
    def find(slug: String): Option[models.Poll] = db withSession { implicit s: Session =>
      val poll = (for {
        poll <- Polls if poll.slug === slug
      } yield {
        poll.*
      }).firstOption
      poll.map { p =>
        val alternatives = (for {
          alternative <- Alternatives if alternative.pollId === p._1
        } yield {
          alternative.*
        }).list.map { a => (a._1, models.Alternative(a._1, a._2)) }.toMap
        val rows = (for {
          vote <- Votes if vote.pollId === p._1
          note <- Notes if note.voteId === vote.id
        } yield vote.id ~ vote.user ~ note.id ~ note.voteId ~ note.value ~ note.alternativeId).list map {
          case (vId, vUser, nId, nVoteId, nValue, nAltId) => ((vId, vUser), (nId, nVoteId, nValue, nAltId))
        }
        val notes = ((rows map { _._2 }).distinct map { n => (n._1, models.Note(n._1, alternatives(n._4), n._3)) }).toMap
        val notesByVote = ((rows map { _._2 }).distinct groupBy { _._2 }).mapValues { ns => ns.map { n => notes(n._1) } }
        val votes = ((rows map { _._1 }).distinct map { v => (v._1, models.Vote(v._1, v._2, notesByVote(v._1))) }).toMap
        models.Poll(p._1, p._2, p._3, p._4, alternatives.values.toSeq, votes.values.toSeq)
      }
    }
    
    def slugs: Seq[String] = db withSession { implicit s: Session =>
      (for (poll <- Polls) yield poll.slug).list
    }
    
    def slug(id: Long): Option[String] = db withSession { implicit s: Session =>
      (for (poll <- Polls if poll.id === id) yield poll.slug).firstOption
    }
  }
  
  object Alternative {
    def create(name: String, pollId: Long): Option[Long] = db withSession { implicit s: Session =>
      Alternatives.noId.insert(name, pollId)
      lastInsertedId
    }
  }
  
  object Vote {
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