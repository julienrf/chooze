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
            case Some(name) => {
              Logger.logger.debug("Poll has been persisted in database")
              Redirect(routes.Application.voteGet(name)).flashing("success" -> "Your poll %s has been created.".format(name))
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
	                  "id" -> ignored(Option.empty[Long]),
	                  "name" -> ignored("")
	              )(Alternative.apply)(Alternative.unapply),
	              "value" -> number
	          )(Note.apply)(Note.unapply)
	      )
	  )(Vote.apply)(Vote.unapply)
  )
  
  def voteGet(name: String) = Action { implicit request =>
    Service.findPoll(name) match {
      case Some(poll) => Ok(views.html.vote(poll, voteForm))
      case None => Redirect(routes.Application.createGet())
    }
  }
  
  def result(name: String) = Action {
    Service.findPoll(name) match {
      case Some(poll) => Ok(views.html.result(poll))
      case None => NotFound
    }
    Ok(views.html.result(Mock.poll))
  }
}