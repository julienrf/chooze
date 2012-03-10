package controllers

import notifications.Notifications
import play.api.mvc.{RequestHeader, PlainResult}
import java.util.Date

trait CacheNotifications extends Cache {
  this: Notifications =>
  
  abstract override def Cached(lastModified: Date)(result: => PlainResult)(implicit request: RequestHeader) = {
    flashNotifications match {
      case Some(_) => result
      case None    => super.Cached(lastModified)(result)
    }
  }
}