package views.html

import controllers.Notification
import Notification._
import play.api.templates._
import play.api.i18n.{Messages, Lang}

object notification {
  def apply(notification: Notification)(implicit lang: Lang): Html = {
    val kindClass = notification.kind match {
      case Success => "success"
      case Error => "error"
    }
    Html(
        """<span class="notification"> - <span class="content %s">%s <span class="button close-notification" title="%s">&times;</span></span></span>"""
            .format(kindClass, HtmlFormat.escape(notification.message), Messages("hide"))
    )
  }
}