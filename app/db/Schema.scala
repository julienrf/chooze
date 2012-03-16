package db

object Schema {
  import org.scalaquery.ql.extended.{ExtendedTable => Table}
  import org.scalaquery.ql.TypeMapper._
  import org.scalaquery.ql._
  import java.sql.Timestamp

  val Alternatives = new Table[(Long, String, Long)]("ALTERNATIVES") {
    def id = column[Long]("ID")
    def name = column[String]("NAME")
    def pollId = column[Long]("POLL_ID")
    def * = id ~ name ~ pollId
    def noId = name ~ pollId
  }
  
  val Notes = new Table[(Long, Long, Int, Long)]("NOTES") {
    def id = column[Long]("ID")
    def voteId = column[Long]("VOTE_ID")
    def value = column[Int]("VALUE")
    def alternativeId = column[Long]("ALTERNATIVE_ID")
    def * = id ~ voteId ~ value ~ alternativeId
    def noId = voteId ~ value ~ alternativeId
  }
  
  val Votes = new Table[(Long, String, Long)]("VOTES") {
    def id = column[Long]("ID")
    def user = column[String]("USER")
    def pollId = column[Long]("POLL_ID")
    def * = id ~ user ~ pollId
    def noId = user ~ pollId
  }
  
  val Polls = new Table[(Long, String, String, String)]("POLLS") {
    def id = column[Long]("ID")
    def name = column[String]("NAME")
    def slug = column[String]("SLUG")
    def description = column[String]("DESCRIPTION")
    def * = id ~ name ~ slug ~ description
    def noId = name ~ slug ~ description
  }
}