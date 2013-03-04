package javascripts

import js.{JSDom, JS}

trait Notification extends JS with JSDom with ReactEvents {

  def handleNotifications() = for {
    click <- eventsOf(Click, document)
    if click.target[Element].classList.contains("button")
    notification <- click.target[Element].closest(_.classList.contains("notification"))
  } notification.remove()

}
