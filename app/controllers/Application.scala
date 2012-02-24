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
	                  "id" -> ignored(Option.empty[Long]),
	                  "name" -> ignored("")
	              )(Alternative.apply)(Alternative.unapply),
	              "value" -> number
	          )(Note.apply)(Note.unapply)
	      )
	  )(Vote.apply)(Vote.unapply)
  )
  
  def voteGet(name: String) = Action { implicit request =>
    Logger.logger.info("Retrieve vote data for poll " + name)
    Service.findPoll(name) match {
      case Some(poll) => Ok(views.html.vote(poll, voteForm))
      case None => NotFound
    }
  }
  
  def votePost(name: String) = Action { 
    /*implicit request =>
    val form = voteForm.bindFromRequest
    form.fold(
        errors => BadRequest(views.html.voteGet(name)),
        vote => {
          
          // pollName: String, user: String, notes: Seq[(Long, Int)]
          Service.vote(name, vote.user, vote.notes.map {
            _.
          })
        }
    )
    */
    Ok(views.html.mock("test"))
  }
  
  def result(name: String) = Action {
    Service.findPoll(name) match {
      case Some(poll) => Ok(views.html.result(poll))
      case None => NotFound
    }
    Ok(views.html.result(Mock.poll))
  }
}