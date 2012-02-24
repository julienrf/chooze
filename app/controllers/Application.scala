package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._

import models._

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
    createForm.bindFromRequest.fold(
        errors => BadRequest(views.html.create(errors)),
        poll => Ok(views.html.mock("Poll created"))
    )
  }
  
  def voteGet(name: String) = Action {
    Ok(views.html.mock("voteGet " + name))
  }
  
  def result(name: String) = Action {
    Ok(views.html.mock("result " + name))
  }
}