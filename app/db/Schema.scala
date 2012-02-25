package db

object Schema {
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
  
  val Polls = new Table[(Long, String, String, String)]("polls") {
    def id = column[Long]("id", O PrimaryKey, O AutoInc)
    def name = column[String]("name")
    def slug = column[String]("slug")
    def description = column[String]("description")
    def * = id ~ name ~ slug ~ description
    def noId = name ~ slug ~ description
  }
  
  val ddl = Alternatives.ddl ++ Notes.ddl ++ Votes.ddl ++ Polls.ddl
}