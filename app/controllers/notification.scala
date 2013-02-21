package controllers

import play.api.mvc.{WithHeaders, Result, Controller, RequestHeader}

case class Notification(message: String, kind: Notification.Kind)

trait Notifications { self: Controller =>

  import scala.language.implicitConversions

  private val NOTIFICATION_KEY = "notification"
  
  implicit def flashNotifications(implicit request: RequestHeader): Option[Notification] = {
    for (message <- flash.get(NOTIFICATION_KEY)) yield Notification(message, Notification.Success)
  }

  implicit class NotifyingOps[A <: Result](result: WithHeaders[A]) {
    def notifying(message: String, kind: Notification.Kind): A = {
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