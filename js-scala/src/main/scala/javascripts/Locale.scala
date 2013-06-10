package javascripts

import scala.js.language.dom.DomReact
import scala.js.language.JS

trait Locale extends JS with DomReact {

  def handleLocaleChange() = for {
    form <- document.find[Form]("form.change-locale")
    change <- events.of(Change, form)
  } form.submit()

}
