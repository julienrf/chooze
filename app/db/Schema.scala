package db

object Schema {
  import scala.slick.driver.PostgresDriver.simple._

  object Alternatives extends Table[(Long, String, Long)]("alternatives") {
    def id =     column[Long  ]("id")
    def name =   column[String]("name")
    def pollId = column[Long  ]("poll_id")
    def * = id ~ name ~ pollId
    def noId = name ~ pollId
  }

  object Notes extends Table[(Long, Long, Int, Long)]("notes") {
    def id =            column[Long]("id")
    def voteId =        column[Long]("vote_id")
    def value =         column[Int ]("value")
    def alternativeId = column[Long]("alternative_id")
    def * = id ~ voteId ~ value ~ alternativeId
    def noId = voteId ~ value ~ alternativeId
  }

  object Votes extends Table[(Long, String, Long)]("votes") {
    def id =     column[Long  ]("id")
    def user =   column[String]("username")
    def pollId = column[Long  ]("poll_id")
    def * = id ~ user ~ pollId
    def noId = user ~ pollId
  }

  object Polls extends Table[(Long, String, String, String)]("polls") {
    def id =          column[Long  ]("id")
    def name =        column[String]("name")
    def slug =        column[String]("slug")
    def description = column[String]("description")
    def * = id ~ name ~ slug ~ description
    def noId = name ~ slug ~ description
  }
}