package shared

import scala.js.JsScala
import forest.Forest

trait Views extends JsScala with Forest {

  object form {

    def inputText(name: Rep[String], value: Rep[String], required: Rep[Boolean], placeholder: Rep[String]) =
      el('input,
        'type->'text,
        'name->name,
        'value->value,
        'class->(if (required) "required" else ""),
        'placeholder->placeholder)()

    def errors(es: Rep[List[String]]) =
      if (es.isEmpty) el('span)()
      else el('div, 'class->"error")(es.mkString2(", "))
  }

  def alternativeTmpl(name: Rep[String], value: Rep[String], required: Rep[Boolean], altMsg: Rep[String], removeMsg: Rep[String], errors: Rep[List[String]] = List()) =
    el('div, 'class->'alternative)(
      el('div, 'style->"display: inline-block; width: 95%;")(
        form.inputText(name, value, required, altMsg)
      ),
      el('span, 'class->"button remove-alternative", 'title->removeMsg)("×"),
      form.errors(errors)
    )

}
