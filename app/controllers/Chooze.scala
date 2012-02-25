package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.i18n.Messages
import models._
import service._

object Chooze extends Controller {
  
  def index = Action {
    Ok(views.html.index())
  }
  
  def showPollForm = Action {
    Ok(views.html.pollForm(pollForm))
  }
  
  def createPoll = Action { implicit request =>
    val form = pollForm.bindFromRequest
    form.fold(
        errors => BadRequest(views.html.pollForm(errors)),
        poll => {
          (for {
            id <- Service.createPoll(poll.name, poll.description, poll.alternatives.map(_.name))
            slug <- Service.pollSlug(id)
          } yield {
            Redirect(routes.Chooze.showVoteForm(slug)).flashing("success" -> Messages("poll.created", poll.name))
          }) getOrElse {
            BadRequest(views.html.pollForm(form))
          }
        }
    )
  }
  
  def showVoteForm(slug: String) = Action { implicit request =>
    Service.findPoll(slug) match {
      case Some(poll) => Ok(views.html.vote(poll, voteForm.fill(Vote(None, "", poll.alternatives.map(Note(None, _, 50))))))
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
              Service.vote(poll.id.get, vote.user, vote.notes map { n => (n.alternative.id.get, n.value) })
              Redirect(routes.Chooze.result(slug))
            }
        )
      }
      case None => NotFound
    }
  }
  
  def result(slug: String) = Action {
    Service.findPoll(slug) match {
      case Some(poll) => Ok(views.html.result(poll))
      case None => NotFound
    }
  }
  
  
  val pollForm: Form[Poll] = Form(
      mapping(
          "id" -> ignored(Option.empty[Long]),
          "name" -> nonEmptyText,
          "slug" -> ignored(""),
          "description" -> nonEmptyText,
          "alternatives" -> seq(
              mapping(
                  "id" -> ignored(None: Option[Long]),
                  "name" -> nonEmptyText
              )(Alternative.apply)(Alternative.unapply)
          ),
          "votes" -> ignored(Seq.empty[Vote])
      )(Poll.apply)(Poll.unapply)
  )
  
  val voteForm: Form[Vote] = Form(
      mapping(
          "id" -> ignored(Option.empty[Long]),
          "user" -> nonEmptyText,
          "notes" -> seq(
              mapping(
                  "id" -> ignored(Option.empty[Long]),
                  "alternative" -> mapping(
                      "id" -> optional(longNumber),
                      "name" -> ignored("")
                  )(Alternative.apply)(Alternative.unapply),
                  "value" -> number
              )(Note.apply)(Note.unapply)
          )
      )(Vote.apply)(Vote.unapply)
  )
}