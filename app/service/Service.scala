package service

import db.Db
import models._

object Service {

  def createPoll(name: String, description: String, alternatives: Seq[String]): Option[String] = {
    Db.Poll.create(name, description, alternatives)
  }
  
  def findPoll(name: String): Option[Poll] = Db.Poll.find(name)
  
  def vote(pollName: String, user: String, notes: Seq[(Long, Int)]): Option[Long] = {
    Db.Vote.create(pollName, user, notes)
  }
}