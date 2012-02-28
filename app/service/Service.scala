package service

import db.Db
import models._
import util.Util
import scala.annotation.tailrec

object Service {

  def createPoll(name: String, description: String, alternatives: Seq[String]): Option[Long] = {
    @tailrec
    def generateUniqueSlug(slug: String, existingSlugs: Seq[String]): String = {
      if (!(existingSlugs contains slug)) {
        slug
      } else {
        val EndsWithNumber = "(.+-)([0-9]+)$".r
        slug match {
          case EndsWithNumber(s, n) => generateUniqueSlug(s + (n.toInt + 1), existingSlugs)
          case s => generateUniqueSlug(s + "-2", existingSlugs)
        }
      }
    }
    // TODO Do not fetch all the slugs until necessary
    Db.Poll.create(name, generateUniqueSlug(Util.slugify(name.take(42)), Db.Poll.slugs), description, alternatives)
  }
  
  def findPoll(slug: String): Option[Poll] = Db.Poll.find(slug)
  
  def pollSlug(id: Long): Option[String] = Db.Poll.slug(id)
  
  def vote(pollId: Long, user: String, notes: Seq[(Long, Int)]): Option[Long] = {
    for {
      alternatives <- Db.Alternative.findAll(pollId)
      if alternatives.size == notes.size
      voteId <- Db.Vote.create(pollId, user, notes)
    } yield voteId
  }
}