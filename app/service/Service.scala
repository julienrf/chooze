package service

import db.Db
import models._

object Service {

  def createPoll(name: String, description: String, alternatives: Seq[String]): Option[Long] = {
    Db.Poll.create(name, description, alternatives)
  }
  
  def findPoll(name: String): Option[Poll] = Db.Poll.find(name)
  
  def vote(pollId: Long, user: String, notes: Seq[(Long, Int)]): Option[Long] = {
    Db.Vote.create(pollId, user, notes)
  }
}