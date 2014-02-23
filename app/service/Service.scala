package service

import db.Db
import models._
import util.Util
import java.util.Date
import org.pegdown.PegDownProcessor

object Service {

  val pegdown = new PegDownProcessor

  def createPoll(name: String, description: String, alternatives: Seq[String]): Option[Long] = {
    Db.Poll.create(name, Util.generateUniqueSlug(Util.slugify(name.take(42)), Db.Poll.slugs), pegdown.markdownToHtml(description), alternatives)
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