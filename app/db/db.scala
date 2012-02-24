package db

object Db {

  // --- Schema definition

  import org.scalaquery.ql.extended.{ExtendedTable => Table}
  import org.scalaquery.ql.extended.H2Driver.Implicit._
  import org.scalaquery.ql.TypeMapper._
  import org.scalaquery.ql._

  val Alternatives = new Table[(Long, String, String)]("alternatives") {
    def id = column[Long]("id", O PrimaryKey, O AutoInc)
    def name = column[String]("name")
    def pollName = column[String]("poll_id")
    def * = id ~ name ~ pollName
    def noId = name ~ pollName
  }
  
  val Notes = new Table[(Long, Long, Int, Long)]("notes") {
    def id = column[Long]("id", O PrimaryKey, O AutoInc)
    def voteId = column[Long]("vote_id")
    def value = column[Int]("value", O Default(0))
    def alternativeId = column[Long]("alternative_id")
    def * = id ~ voteId ~ value ~ alternativeId
    def noId = voteId ~ value ~ alternativeId
  }
  
  val Votes = new Table[(Long, String, String)]("votes") {
    def id = column[Long]("id", O PrimaryKey, O AutoInc)
    def user = column[String]("user")
    def pollName = column[String]("poll_id")
    def * = id ~ user ~ pollName
    def noId = user ~ pollName
  }
  
  val Polls = new Table[(String, String)]("polls") {
    def name = column[String]("name", O PrimaryKey)
    def description = column[String]("description")
    def * = name ~ description
  }
  
  val ddl = Alternatives.ddl ++ Notes.ddl ++ Votes.ddl ++ Polls.ddl
  
  
  // --- Query helpers
  
  import play.api.db.DB
  import org.scalaquery.session.{Session, Database}
  import play.api.Play.current
  
  val db = Database.forDataSource(DB.getDataSource())
  
  def lastInsertedId[T](implicit s: Session, tm: TypeMapper[T]) = Query(SimpleFunction.nullary[T]("scope_identity")).first
  
  object Poll {
    def create(name: String, description: String, alternatives: Seq[String]): Option[String] = db withSession { implicit s: Session =>
      Polls.insert(name, description)
      for (alternative <- alternatives) {
        Alternative.create(alternative, name)
      }
      Option(name)
    }
    
    def find(pollName: String): Option[models.Poll] = db withSession { implicit s: Session =>
      val rows = (for {
        poll <- Polls if poll.name === pollName
        alternative <- Alternatives if alternative.pollName === poll.name
        vote <- Votes if vote.pollName === poll.name
        note <- Notes if note.voteId === vote.id
      } yield poll.name ~ poll.description ~ alternative.id ~ alternative.name ~ vote.id ~ vote.user ~ note.id ~ note.voteId ~ note.value ~ note.alternativeId).list map {
        case (pName, pDescr, aId, aName, vId, vUser, nId, nVoteId, nValue, nAltId) => ((pName, pDescr), (aId, aName), (vId, vUser), (nId, nVoteId, nValue, nAltId))
      }
      val alternatives = ((rows map { _._2 }).distinct map { a => (a._1, models.Alternative(a._2)) }).toMap
      val notes = ((rows map { _._4 }).distinct map { n => (n._1, models.Note(alternatives(n._4), n._3)) }).toMap
      val notesByVote = ((rows map { _._4 }).distinct groupBy { _._2 }).mapValues { ns => ns.map { n => notes(n._1) } }
      val votes = ((rows map { _._3 }).distinct map { v => (v._1, models.Vote(v._2, notesByVote(v._1))) }).toMap
      val polls = (rows map { _._1 }).distinct map { p => models.Poll(p._1, p._2, alternatives.values.toSeq, votes.values.toSeq) }
      polls.headOption
    }
  }
  
  object Alternative {
    def create(name: String, pollName: String): Option[Long] = db withSession { implicit s: Session =>
      Alternatives.noId.insert(name, pollName)
      Option(lastInsertedId[Long])
    }
  }
  
  object Vote {
    def create(pollName: String, user: String, notes: Seq[(Long, Int)]): Option[Long] = db withSession { implicit s: Session =>
      Votes.noId.insert(user, pollName)
      val voteId = lastInsertedId[Long]
      
      for ((alternativeId, note) <- notes) {
        Note.create(voteId, note, alternativeId)
      }
      
      Option(voteId)
    }
  }
  
  object Note {
    def create(voteId: Long, note: Int, alternativeId: Long): Option[Long] = db withSession { implicit s: Session =>
      Notes.noId.insert(voteId, note, alternativeId)
      Option(lastInsertedId[Long])
    }
  }
}