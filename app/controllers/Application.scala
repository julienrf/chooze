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
        errors => BadRequest(views.html.create(errors)),
        poll => {
          Service.createPoll(poll.name, poll.description, poll.alternatives.map(_.name)) match {
            case Some(poll) => {
              Redirect(routes.Application.voteGet(poll.name)).flashing("success" -> "Your poll %s has been created.".format(poll.name))
            }
            case None => BadRequest(views.html.create(form))
          }
        }
    )
  }
  
  def voteGet(name: String) = Action { implicit request =>
    //val poll = Service.findPoll()
    val poll = new Poll("name", "description", Nil, Nil);
    Ok(views.html.vote(poll))
  }
  
  def result(name: String) = Action {
    Ok(views.html.mock("result " + name))
  }
}