package javascripts

import js.{JS, JSDom, Casts}
import shared.Views

trait PollForm extends JS with JSDom with Views with ReactEvents with Casts {

  def pollForm(altMsg: Rep[String], removeMsg: Rep[String]) = for {
    form <- document.find("form")
    alternatives <- document.find(".alternatives")
    addBtn <- document.find(".add-alternative")
  } {

    for {
      click <- eventsOf(Click, form)
      if click.target[Element].className == "button remove-alternative"
      alternative <- click.target[Element].closest(_.className == "alternative")
    } {
      alternative.remove()
      alternatives.findAll[Input]("input").foreachWithIndex { (input, i) =>
        input.name_=("alternatives[" + i + "]")
      }
    }

    addBtn.on(Click) { _ =>
      val name = "alternatives[" + alternatives.findAll(".alternative").size + "]"
      val altEl = alternativeTmpl(name, "", true, altMsg, removeMsg, List()).as[Element]
      alternatives.appendChild(altEl)
      altEl.find[Input]("input").foreach(_.focus())
    }
  }
}
