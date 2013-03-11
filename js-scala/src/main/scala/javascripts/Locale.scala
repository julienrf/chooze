package javascripts

import js.{DomReact, JS, JSDom}

trait Locale extends JS with JSDom with DomReact {

  def handleLocaleChange() = for {
    form <- document.find[Form]("form.change-locale")
    change <- events.of(Change, form)
  } form.submit()

}
