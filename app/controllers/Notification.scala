package controllers

import play.api.mvc.{Controller, RequestHeader}

case class Notification(message: String)

trait Notifications {
  self: Controller =>
  implicit def notifications(implicit request: RequestHeader): Option[Notification] = {
    for (message <- flash.get("notification")) yield Notification(message)
  }
}