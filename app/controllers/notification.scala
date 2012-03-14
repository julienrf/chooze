package controllers

import play.api.mvc.{Controller, RequestHeader, PlainResult}

case class Notification(message: String, kind: Notification.Kind)

trait Notifications {
  self: Controller =>

  private val NOTIFICATION_KEY = "notification"
  
  implicit def flashNotifications(implicit request: RequestHeader): Option[Notification] = {
    for (message <- flash.get(NOTIFICATION_KEY)) yield Notification(message, Notification.Success)
  }

  // TODO I’d like to return a T instead of a PlainResult
  // TODO Handle notification kind
  implicit def resultNotifying[T <: PlainResult](result: T) = new {
    def notifying(message: String, kind: Notification.Kind): PlainResult = {
      result.flashing(NOTIFICATION_KEY -> message)
    }
  }

  def notify(message: String, kind: Notification.Kind) = Some(Notification(message, kind))
}

object Notification {
  sealed trait Kind
  case object Success extends Kind
  case object Error extends Kind
}