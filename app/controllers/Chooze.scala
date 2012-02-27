package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.i18n.Messages
import models._
import service._

object Chooze extends Controller with Notifications {
  
  def index = Action { implicit request =>
    Ok(views.html.index())
  }
  
  def showPollForm = Action { implicit request =>
    Ok(views.html.pollForm(pollForm))
  }
  
  def createPoll = Action { implicit request =>
    val form = pollForm.bindFromRequest
    form.fold(
        errors => BadRequest(views.html.pollForm(errors)),
        poll => {
          (for {
            id <- (Service.createPoll _).tupled(poll)
            slug <- Service.pollSlug(id)
          } yield {
            Redirect(routes.Chooze.showVoteForm(slug)).flashing("notification" -> Messages("poll.created", poll._1))
          }) getOrElse {
            BadRequest(views.html.pollForm(form))
          }
        }
    )
  }
  
  def showVoteForm(slug: String) = Action { implicit request =>
    // TODO fetch only description and alternatives (don’t fetch the votes)
    Service.findPoll(slug) match {
      case Some(poll) => Ok(views.html.vote(poll, voteForm.fill(("", poll.alternatives.map(_.id.get -> 50)))))
      case None => NotFound
    }
  }
  
  def vote(slug: String) = Action { implicit request =>
    Service.findPoll(slug) match {
      case Some(poll) => {
        val form = voteForm.bindFromRequest
        form.fold(
            errors => BadRequest(views.html.vote(poll, errors)),
            vote => {
              Service.vote(poll.id.get, vote._1, vote._2)
              Redirect(routes.Chooze.result(slug)).flashing("notification" -> Messages("vote.registered"))
            }
        )
      }
      case None => NotFound
    }
  }
  
  def result(slug: String) = Action { implicit request =>
    Service.findPoll(slug) match {
      case Some(poll) => Ok(views.html.result(poll))
      case None => NotFound
    }
  }
  
  
  val pollForm = Form(
      tuple(
        "name" -> nonEmptyText,
        "description" -> nonEmptyText,
        "alternatives" -> seq(nonEmptyText).verifying("two.alternatives.min", _.length >= 2) // TODO fix that
      )
  )
  
  val voteForm = Form(
      tuple(
          "user" -> nonEmptyText,
          "notes" -> seq(
              tuple(
                  "alternative" -> longNumber,
                  "value" -> number
              )
          )
      )
  )
}