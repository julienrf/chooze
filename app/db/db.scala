package db

object Db {

  // --- Schema definition

  import org.scalaquery.ql.extended.{ExtendedTable => Table}
  import org.scalaquery.ql.extended.H2Driver.Implicit._
  import org.scalaquery.ql.TypeMapper._
  import org.scalaquery.ql._

  val Alternatives = new Table[(Long, String, Long)]("alternatives") {
    def id = column[Long]("id", O PrimaryKey, O AutoInc)
    def name = column[String]("name")
    def pollId = column[Long]("poll_id")
    def * = id ~ name ~ pollId
    def noId = name ~ pollId
  }
  
  val Notes = new Table[(Long, Long, Int, Long)]("notes") {
    def id = column[Long]("id", O PrimaryKey, O AutoInc)
    def voteId = column[Long]("vote_id")
    def value = column[Int]("value", O Default(0))
    def alternativeId = column[Long]("alternative_id")
    def * = id ~ voteId ~ value ~ alternativeId
    def noId = voteId ~ value ~ alternativeId
  }
  
  val Votes = new Table[(Long, String, Long)]("votes") {
    def id = column[Long]("id", O PrimaryKey, O AutoInc)
    def user = column[String]("user")
    def pollId = column[Long]("poll_id")
    def * = id ~ user ~ pollId
    def noId = user ~ pollId
  }
  
  val Polls = new Table[(Long, String, String)]("polls") {
    def id = column[Long]("id", O PrimaryKey, O AutoInc)
    def name = column[String]("name")
    def description = column[String]("description")
    def * = id ~ name ~ description
    def noId = name ~ description
  }
  
  val ddl = Alternatives.ddl ++ Notes.ddl ++ Votes.ddl ++ Polls.ddl
  
  
  // --- Query helpers
  
  import play.api.db.DB
  import org.scalaquery.session.{Session, Database}
  import play.api.Play.current
  
  val db = Database.forDataSource(DB.getDataSource())
  
  def lastInsertedId(implicit s: Session) = Query(SimpleFunction.nullary[Long]("scope_identity")).first
  
  object Poll {
    def create(name: String, description: String, alternatives: Seq[String]): Option[Long] = db withSession { implicit s: Session =>
      Polls.noId.insert(name, description)
      val pollId = lastInsertedId
      for (alternative <- alternatives) {
        Alternative.create(alternative, pollId)
      }
      Option(pollId)
    }
    
    def find(id: Long): Option[models.Poll] = db withSession { implicit s: Session =>
      val rows = (for {
        poll <- Polls if poll.id === id
        alternative <- Alternatives if alternative.pollId === poll.id
        vote <- Votes if vote.pollId === poll.id
        note <- Notes if note.voteId === vote.id
      } yield poll.id ~ poll.name ~ poll.description ~ alternative.id ~ alternative.name ~ vote.id ~ vote.user ~ note.id ~ note.voteId ~ note.value ~ note.alternativeId).list map {
        case (pId, pName, pDescr, aId, aName, vId, vUser, nId, nVoteId, nValue, nAltId) => ((pId, pName, pDescr), (aId, aName), (vId, vUser), (nId, nVoteId, nValue, nAltId))
      }
      val alts = ((rows map { _._2 }).distinct map { a => (a._1, models.Alternative(a._2)) }).toMap
      val ns = ((rows map { _._4 }).distinct map { n => (n._1, models.Note(alts(n._4), n._3)) }).toMap
      val notesByVote = ((rows map { _._4 }).distinct groupBy { _._2 }).mapValues { notes => notes.map { n => ns(n._1) } }
      val vs = ((rows map { _._3 }).distinct map { v => (v._1, models.Vote(v._2, notesByVote(v._1))) }).toMap
      val ps = (rows map { _._1 }).distinct map { p => models.Poll(p._2, p._3, alts.values.toSeq, vs.values.toSeq) }
      ps.headOption
    }
  }
  
  object Alternative {
    def create(name: String, pollId: Long): Option[Long] = db withSession { implicit s: Session =>
      Alternatives.noId.insert(name, pollId)
      Option(lastInsertedId)
    }
  }
  
  object Vote {
    def create(pollId: Long, user: String, notes: Seq[(Long, Int)]): Option[Long] = db withSession { implicit s: Session =>
      Votes.noId.insert(user, pollId)
      val voteId = lastInsertedId
      
      for ((alternativeId, note) <- notes) {
        Note.create(voteId, note, alternativeId)
      }
      
      Option(voteId)
    }
  }
  
  object Note {
    def create(voteId: Long, note: Int, alternativeId: Long): Option[Long] = db withSession { implicit s: Session =>
      Notes.noId.insert(voteId, note, alternativeId)
      Option(lastInsertedId)
    }
  }
}