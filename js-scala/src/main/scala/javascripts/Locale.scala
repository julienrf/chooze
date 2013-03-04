package javascripts

import js.{JS, JSDom}

trait Locale extends JS with JSDom {

  def handleLocaleChange() = for (form <- document.find[Form]("form.change-locale")) {
    form.on(Change) { _ => form.submit() }
  }

}
