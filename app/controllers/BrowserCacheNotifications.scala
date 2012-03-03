package controllers

import notifications.Notifications
import play.api.mvc.{RequestHeader, PlainResult}
import java.util.Date

trait BrowserCacheNotifications extends BrowserCache {
  this: Notifications =>
  
  abstract override def browserCached(lastModified: Date)(result: => PlainResult)(implicit request: RequestHeader) = {
    flashNotifications match {
      case Some(_) => result
      case None    => super.browserCached(lastModified)(result)
    }
  }
}