package controllers

import play.api.mvc.{RequestHeader, PlainResult}
import play.api.i18n.Lang
import java.util.Date

trait CacheNotifications extends Cache {
  this: Notifications =>
  
  abstract override def Cached(lastModified: Date)(result: => PlainResult)(implicit request: RequestHeader, lang: Lang) = {
    flashNotifications match {
      case Some(_) => result
      case None    => super.Cached(lastModified)(result)
    }
  }
}