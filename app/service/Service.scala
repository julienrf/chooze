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
    Db.Poll.create(name, generateUniqueSlug(Util.slugify(name.take(32)), Db.Poll.slugs), description, alternatives)
  }
  
  def findPoll(slug: String): Option[Poll] = Db.Poll.find(slug)
  
  def pollSlug(id: Long): Option[String] = Db.Poll.slug(id)
  
  def vote(pollId: Long, user: String, notes: Seq[(Long, Int)]): Option[Long] = {
    Db.Vote.create(pollId, user, notes)
  }
}