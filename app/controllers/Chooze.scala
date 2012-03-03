package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.i18n.Messages
import models._
import service._
import notifications.Notifications

object Chooze extends Controller with Cache with Notifications with CacheNotifications with AuthenticationToken {

  def index = Action { implicit request =>
    cached {
      Ok(views.html.index())
    }
  }

  def showPollForm = Action { implicit request =>
    authenticationToken(pollForm, "auth-token") { form =>
      Ok(views.html.pollForm(form))
    }
  }

  def createPoll = Action { implicit request =>
    val form = pollForm.bindFromRequest
    form.fold(
        errors => {
          implicit val formErrors = notify(Messages("form.fix.errors"), notifications.Error)
          BadRequest(views.html.pollForm(errors))
        },
        {
          case (token, name, descr, alts) => {
            checkAuthenticity(token) {
              (for {
                id <- Service.createPoll(name, descr, alts)
                slug <- Service.pollSlug(id)
              } yield {
                Redirect(routes.Chooze.showVoteForm(slug))
                  .notifying(Messages("poll.created", name), notifications.Success)
              })
            } getOrElse {
                implicit val error = notify(Messages("internal.error"), notifications.Error)
                BadRequest(views.html.pollForm(form))
              }
            }
        }
    )
  }

  def showVoteForm(slug: String) = Action { implicit request =>
    // TODO fetch only description and alternatives (don’t fetch the votes)
    Service.findPoll(slug) match {
      case Some(poll) => {
        val username = (for {
          cookie <- request.cookies.get("username")
          username = cookie.value
        } yield username) getOrElse ("")
        authenticationToken(voteForm, "auth-token") { form =>
          Ok(views.html.vote(poll, form.fill((form("auth-token").value.get, username, poll.alternatives.map(_.id -> 50)))))
        }
      }
      case None => NotFound(views.html.notFound())
    }
  }

  def vote(slug: String) = Action { implicit request =>
    Service.findPoll(slug) match {
      case Some(poll) => {
        val form = voteForm.bindFromRequest
        form.fold(
            errors => {
              implicit val formErrors = notify(Messages("form.fix.errors"), notifications.Error)
              BadRequest(views.html.vote(poll, errors))
            },
            {
              case (token, user, notes) => {
                checkAuthenticity(token) {
                  // TODO handle failure
                  Service.vote(poll.id, user, notes)
                  Some(Redirect(routes.Chooze.result(slug))
                    .notifying(Messages("vote.registered"), notifications.Success)
                    .withCookies(Cookie("username", user)))
                } getOrElse {
                  implicit val error = notify(Messages("internal.error"), notifications.Error)
                  BadRequest(views.html.vote(poll, form))
                }
              }
            }
        )
      }
      case None => NotFound(views.html.notFound())
    }
  }

  def result(slug: String) = Action { implicit request =>
    Service.pollLastModified(slug) match {
      case Some(lastModified) => cached(lastModified) {
        Service.findPoll(slug) match {
          case Some(poll) => Ok(views.html.result(poll))
          case None       => NotFound(views.html.notFound())
        }
      }
      case None => NotFound(views.html.notFound())
    }
  }


  val pollForm = Form(
      tuple(
        "auth-token" -> text,
        "name" -> nonEmptyText,
        "description" -> nonEmptyText,
        "alternatives" -> seq(nonEmptyText).verifying("two.alternatives.min", _.length >= 2) // TODO fix that
      )
  )

  val voteForm = Form(
      tuple(
          "auth-token" -> text,
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