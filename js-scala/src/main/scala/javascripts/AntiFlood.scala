package javascripts

import js.{JSDom, JS}

trait AntiFlood extends JS with JSDom {

  def setupAntiFlood() = for (form <- document.find[Element]("form")) {
    val buttons = form.findAll[Input]("input[type=submit], button:not([type=button])")
    form.on(Submit) { _ =>
      buttons.foreach(_.disabled_=(true))
    }
  }

}
