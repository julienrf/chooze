package javascripts

import js.{DomReact, JSDom, JS}

trait AntiFlood extends JS with JSDom with DomReact {

  def setupAntiFlood() = for (form <- document.findAll[Element]("form")) {
    val buttons = form.findAll[Input]("input[type=submit], button:not([type=button])")
    for {
      submit <- events.of(Submit, form)
      button <- buttons
    } button.disabled_=(true)
  }

}
