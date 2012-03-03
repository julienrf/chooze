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

object Chooze extends Controller with Cache with Notifications with CacheNotifications {

  def index = Action { implicit request =>
    cached {
      Ok(views.html.index())
    }
  }

  def showPollForm = Action { implicit request =>
    cached {
      Ok(views.html.pollForm(pollForm))
    }
  }

  def createPoll = Action { implicit request =>
    val form = pollForm.bindFromRequest
    form.fold(
        errors => {
          implicit val formErrors = notify(Messages("form.fix.errors"), notifications.Error)
          BadRequest(views.html.pollForm(errors))
        },
        poll => {
          (for {
            id <- (Service.createPoll _).tupled(poll)
            slug <- Service.pollSlug(id)
          } yield {
            Redirect(routes.Chooze.showVoteForm(slug))
              .notifying(Messages("poll.created", poll._1), notifications.Success)
          }) getOrElse {
            implicit val error = notify(Messages("internal.error"), notifications.Error)
            BadRequest(views.html.pollForm(form))
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
        Ok(views.html.vote(poll, voteForm.fill((username, poll.alternatives.map(_.id -> 50)))))
      }
      case None => NotFound
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
            vote => {
              // TODO handle failure
              Service.vote(poll.id, vote._1, vote._2)
              Redirect(routes.Chooze.result(slug))
                .notifying(Messages("vote.registered"), notifications.Success)
                .withCookies(Cookie("username", vote._1))
            }
        )
      }
      case None => NotFound
    }
  }

  def result(slug: String) = Action { implicit request =>
    Service.pollLastModified(slug) match {
      case Some(lastModified) => cached(lastModified) {
        Service.findPoll(slug) match {
          case Some(poll) => Ok(views.html.result(poll))
          case None       => NotFound
        }
      }
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