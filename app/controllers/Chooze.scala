package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.i18n.Messages
import models._
import service._
import notifications.{Notifications, Success, Error}

object Chooze extends Controller with Cache with Notifications with CacheNotifications with AuthenticationToken {

  def index = Action { implicit request =>
    cached {
      Ok(views.html.index())
    }
  }

  def showPollForm = Action { implicit request =>
    authenticationToken { implicit token =>
      Ok(views.html.pollForm(pollForm))
    }
  }

  def createPoll = Action { implicit request =>
    val form = pollForm.bindFromRequest
    form.fold(
        errors => {
          implicit val formErrors = notify(Messages("form.fix.errors"), Error)
          authenticationToken { implicit token =>
            BadRequest(views.html.pollForm(errors))
          }
        },
        {
          case (name, descr, alts) => {
            for {
              _ <- checkAuthenticity
              id <- Service.createPoll(name, descr, alts)
              slug <- Service.pollSlug(id)
            } yield {
              Redirect(routes.Chooze.showVoteForm(slug))
                .notifying(Messages("poll.created", name), notifications.Success)
            }
          } getOrElse {
              implicit val error = notify(Messages("internal.error"), notifications.Error)
              authenticationToken { implicit token =>
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
        authenticationToken { implicit token =>
          Ok(views.html.vote(poll, voteForm.fill((username, poll.alternatives.map(_.id -> 50)))))
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
              implicit val formErrors = notify(Messages("form.fix.errors"), Error)
              authenticationToken { implicit token =>
                BadRequest(views.html.vote(poll, errors))
              }
            },
            {
              case (user, notes) => {
                for {
                  _ <- checkAuthenticity
                  _ <- Service.vote(poll.id, user, notes)
                } yield {
                  Redirect(routes.Chooze.result(slug))
                    .notifying(Messages("vote.registered"), Success)
                    .withCookies(Cookie("username", user))
                }
              } getOrElse {
                implicit val error = notify(Messages("internal.error"), Error)
                authenticationToken { implicit token =>
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
        "name" -> nonEmptyText,
        "description" -> nonEmptyText,
        "alternatives" -> list(nonEmptyText).verifying("two.alternatives.min", _.length >= 2)
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