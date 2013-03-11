package javascripts

import js.{DomReact, JS, JSDom, Casts}
import shared.Views

trait PollForm extends JS with JSDom with Views with DomReact with Casts {

  def pollForm(altMsg: Rep[String], removeMsg: Rep[String]) = for {
    form <- document.find("form")
    alternatives <- document.find(".alternatives")
    addBtn <- document.find(".add-alternative")
  } {

    for {
      click <- events.filtering(Click, form)(_.target[Element].className == "button remove-alternative")
      alternative <- click.target[Element].closest(_.className == "alternative")
    } {
      alternative.remove()
      alternatives.findAll[Input]("input").foreachWithIndex { (input, i) =>
        input.name_=("alternatives[" + i + "]")
      }
    }

    for (click <- events.of(Click, addBtn)) {
      val name = "alternatives[" + alternatives.findAll(".alternative").size + "]"
      val altEl = alternativeTmpl(name, "", true, altMsg, removeMsg).as[Element]
      alternatives.appendChild(altEl)
      altEl.find[Input]("input").foreach(_.focus())
    }
  }
}
