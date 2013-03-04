package javascripts

import scala.js.{JS, JSDom}

trait Vote extends JS with JSDom with ReactEvents {

  def vote() = for (form <- document.find("form")) {
    val inputs = form.findAll[Input](".alternative > input[type=range]")
    for {
      e <- eventsOf(Change, form)
      input <- inputs if input == e.target
      name <- input.prev(_.className == "name")
    } name.style.fontWeight_=(100 + Integer.parseInt(input.value) * 8)
  }

}
