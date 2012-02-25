package test

import play.api.test._
import play.api.test.Helpers._

import org.specs2.mutable._

import db.Db

class DbTest extends Specification {
  "a Db" can {
    "create a poll" >> {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val maybeId = Db.Poll.create("Summer holidays", "summer-holidays", "Where do we go this summer?", List("Greece", "Monte Negro"))
        maybeId match {
          case Some(id) => {
            Db.Poll.find("Summer holidays") match {
              case Some(poll) => {
                poll.name must be equalTo ("Summer holidays")
                poll.description must be equalTo ("Where do we go this summer?")
                poll.alternatives must contain ("Greece")
                poll.votes must beEmpty
              }
              case None => failure("No poll found")
            }
          }
          case None => failure("No poll created")
        }
      }
    }
  }
}