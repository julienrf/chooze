package javascripts

import scala.js.{JS, JSDom, React}

trait ReactEvents extends JS with JSDom with React {

  def eventsOf[A : Manifest](event: EventName[A], e: Rep[EventTarget] = window): Rep[Events[A]] = Events[A] { observer =>
    e.on(event) { e => observer(e) }
  }

}
