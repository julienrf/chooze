package javascripts

import js.{DomReact, JSDom, JS}

trait Notification extends JS with JSDom with DomReact {

  def handleNotifications() = for {
    click <- events.filtering(Click, document)(_.target[Element].classList.contains("close-notification"))
    notification <- click.target[Element].closest(_.classList.contains("notification"))
  } notification.remove()

}
