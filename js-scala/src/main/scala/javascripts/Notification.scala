package javascripts

import scala.js.language.dom.DomReact
import scala.js.language.JS

trait Notification extends JS with DomReact {

  def handleNotifications() = for {
    click <- events.filtering(Click, document)(_.target[Element].classList.contains("close-notification"))
    notification <- click.target[Element].closest(_.classList.contains("notification"))
  } notification.remove()

}
