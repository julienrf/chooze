package service

import db.Db
import models._

object Service {

  def createPoll(name: String, description: String, alternatives: Seq[String]): Option[Poll] = {
    for {
      id <- Db.Poll.create(name, description, alternatives)
      poll <- findPoll(id)
    } yield poll
  }
  
  def findPoll(id: Long): Option[Poll] = Db.Poll.find(id)
}