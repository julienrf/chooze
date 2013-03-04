package views.html

import play.api.data.Field
import play.api.i18n.{Messages, Lang}

object util {
  def errors(field: Field)(implicit lang: Lang): List[String] =
    field.errors.to[List].map(e => Messages(e.message))
  def inputText(field: Field, placeholder: String) =
    forest.form.inputText(field.name, field.value.getOrElse(""), field.constraints.find(_._1 == "constraint.required").isDefined, placeholder)
}
