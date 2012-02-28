package controllers

import play.api.mvc.{Controller, RequestHeader, PlainResult}

// TODO Notification kinds (error, success)
case class Notification(message: String)

trait Notifications {
  self: Controller =>

  private val NOTIFICATION_KEY = "notification"
  
  implicit def notifications(implicit request: RequestHeader): Option[Notification] = {
    for (message <- flash.get(NOTIFICATION_KEY)) yield Notification(message)
  }

  implicit def resultNotifying[T <: PlainResult](result: T) = new {
    def notifying(message: String): T = {
      result.flashing((NOTIFICATION_KEY, message))
      result
    }
  }

  def notify(message: String) = Some(Notification(message))
}