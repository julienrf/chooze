package views.html

object forest {
  object generated {
    /*****************************************
  Emitting Generated Code                  
*******************************************/
class AlternativeTmpl extends ((java.lang.String, java.lang.String, Boolean, java.lang.String, java.lang.String, scala.collection.immutable.List[java.lang.String])=>(scala.xml.Node)) {
def apply(x0:java.lang.String, x1:java.lang.String, x2:Boolean, x3:java.lang.String, x4:java.lang.String, x5:scala.collection.immutable.List[java.lang.String]): scala.xml.Node = {
val x6 = if (x2) {
"required"
} else {
""
}
val x8 = <input name={x0}  placeholder={x3}  class={x6}  type={"text"}  value={x1} />
val x10 = <div style={"display: inline-block; width: 95%;"}>{x8}</div>
val x11 = {xml.Text("×")}
val x13 = <span class={"button remove-alternative"}  title={x4}>{x11}</span>
val x14 = x5.isEmpty
val x22 = if (x14) {
val x15 = <span />
x15
} else {
val x17 = x5.mkString(", ")
val x18 = {xml.Text(x17)}
val x20 = <div class={"error"}>{x18}</div>
x20
}
val x24 = <div class={"alternative"}>{List(x10, x13, x22)}</div>
x24
}
}
/*****************************************
  End of Generated Code                  
*******************************************/

    /*****************************************
  Emitting Generated Code                  
*******************************************/
class InputText extends ((java.lang.String, java.lang.String, Boolean, java.lang.String)=>(scala.xml.Node)) {
def apply(x26:java.lang.String, x27:java.lang.String, x28:Boolean, x29:java.lang.String): scala.xml.Node = {
val x30 = if (x28) {
"required"
} else {
""
}
val x31 = <input name={x26}  placeholder={x29}  class={x30}  type={"text"}  value={x27} />
x31
}
}
/*****************************************
  End of Generated Code                  
*******************************************/

    /*****************************************
  Emitting Generated Code                  
*******************************************/
class Errors extends ((scala.collection.immutable.List[java.lang.String])=>(scala.xml.Node)) {
def apply(x33:scala.collection.immutable.List[java.lang.String]): scala.xml.Node = {
val x34 = x33.isEmpty
val x42 = if (x34) {
val x35 = <span />
x35
} else {
val x37 = x33.mkString(", ")
val x38 = {xml.Text(x37)}
val x40 = <div class={"error"}>{x38}</div>
x40
}
x42
}
}
/*****************************************
  End of Generated Code                  
*******************************************/

  }

  object alternativeTmpl extends generated.AlternativeTmpl
  object form {
    object inputText extends generated.InputText
    object errors extends generated.Errors
  }
}

    
