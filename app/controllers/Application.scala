package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import models._
import service._
import models.Poll
import models.Poll

object Application extends Controller {
  val createForm: Form[Poll] = Form(
      mapping(
          "id" -> ignored(Option.empty[Long]),
          "name" -> nonEmptyText,
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
  
  def createGet = Action {
    Ok(views.html.create(createForm))
  }
  
  def createPost = Action { implicit request =>
    val form = createForm.bindFromRequest
    form.fold(
        errors => { Logger.logger.warn("Create poll form contains errors."); BadRequest(views.html.create(errors)) },
        poll => {
          Logger.logger.debug("Create poll form is valid.")
          Service.createPoll(poll.name, poll.description, poll.alternatives.map(_.name)) match {
            case Some(id) => {
              Redirect(routes.Application.voteGet(poll.name)).flashing("success" -> "Your poll %s has been created.".format(poll.name))
            }
            case None => {
              Logger.logger.warn("Poll has not been persisted in database")
              BadRequest(views.html.create(form))
            }
          }
        }
    )
  }
  
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
  
  def voteGet(name: String) = Action { implicit request =>
    Service.findPoll(name) match {
      case Some(poll) => Ok(views.html.vote(poll, voteForm.fill(Vote(None, "", poll.alternatives.map(Note(None, _, 50))))))
      case None => NotFound
    }
  }
  
  def votePost(pollName: String) = Action { implicit request =>
    Service.findPoll(pollName) match {
      case Some(poll) => {
        val form = voteForm.bindFromRequest
        form.fold(
            errors => BadRequest(views.html.vote(poll, errors)),
            vote => {
              Service.vote(poll.id.get, vote.user, vote.notes map { n => (n.alternative.id.get, n.value) })
              Redirect(routes.Application.result(pollName))
            }
        )
      }
      case None => NotFound
    }
  }
  
  def result(name: String) = Action {
    Service.findPoll(name) match {
      case Some(poll) => Ok(views.html.result(poll))
      case None => NotFound
    }
  }
}