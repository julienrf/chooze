import com.google.common.collect.Lists
import com.google.javascript.jscomp.{CommandLineRunner, CompilationLevel, CompilerOptions, JSSourceFile}
import scala.js._
import forest._
import java.io._
import javascripts._
import shared._

object Generator extends App {
  val jsProg = new Chooze with PollForm with Vote with Views with JSDomExp with CastsCheckedExp with ForestExp with JSExpOpt
  val gen = new JSGen with JSGenDom with GenCastChecked with JSGenForest { val IR: jsProg.type = jsProg }

  def asString(f: PrintWriter => Unit) = {
    val str = new StringWriter()
    f(new PrintWriter(str))
    str.toString()
  }

  def compress(source: String): String = {
    val compiler = new com.google.javascript.jscomp.Compiler
    val options = new CompilerOptions
    CompilationLevel.SIMPLE_OPTIMIZATIONS.setOptionsForCompilationLevel(options)

    compiler.compile(CommandLineRunner.getDefaultExterns(), Lists.newArrayList(JSSourceFile.fromCode("input.js", source)), options)
    compiler.toSource
    source
  }

  import virtualization.lms.internal.GenericCodegen
  def emitSource6[T1 : Manifest, T2 : Manifest, T3 : Manifest, T4 : Manifest, T5 : Manifest, T6 : Manifest, R : Manifest](g: GenericCodegen)(f: (g.IR.Exp[T1], g.IR.Exp[T2], g.IR.Exp[T3], g.IR.Exp[T4], g.IR.Exp[T5], g.IR.Exp[T6]) => g.IR.Exp[R], name: String, stream: PrintWriter) {
    import g.IR._
    val s1 = fresh[T1]
    val s2 = fresh[T2]
    val s3 = fresh[T3]
    val s4 = fresh[T4]
    val s5 = fresh[T5]
    val s6 = fresh[T6]
    val body = g.reifyBlock(f(s1, s2, s3, s4, s5, s6))
    g.emitSource(List(s1, s2, s3, s4, s5, s6), body, name, stream)
  }

  // TODO Minification
  val js = new PrintWriter("../app/javascripts/JavaScripts.scala")
  js.println(
    s"""package javascripts
      |
      |object JavaScripts {
      |
      |  val chooze = ${"\"\"\"" + compress(asString(out => gen.emitExecution(jsProg.chooze(), out))) + "\"\"\""}
      |
      |  val vote = ${"\"\"\"" + compress(asString(out => gen.emitExecution(jsProg.vote(), out))) + "\"\"\""}
      |
      |  val pollForm = ${"\"\"\"" + compress(asString(out => gen.emitSource2(jsProg.pollForm, "pollForm", out))) + "\"\"\""}
      |
      |}""".stripMargin)
  js.close()

  val scalaProg = new Views with ForestExp with JsScalaExpOpt
  val scalaGen = new ScalaGenForest with ScalaGenJsScala { val IR: scalaProg.type = scalaProg }
  val forest = new PrintWriter("../app/views/html/Forest.scala")
  forest.println(
    s"""package views.html
      |
      |object forest {
      |  object generated {
      |    ${asString(out => emitSource6(scalaGen)(scalaProg.alternativeTmpl, "AlternativeTmpl", out))}
      |    ${asString(out => scalaGen.emitSource4(scalaProg.form.inputText, "InputText", out))}
      |    ${asString(out => scalaGen.emitSource(scalaProg.form.errors, "Errors", out))}
      |  }
      |
      |  object alternativeTmpl extends generated.AlternativeTmpl
      |  object form {
      |    object inputText extends generated.InputText
      |    object errors extends generated.Errors
      |  }
      |}""".stripMargin)
  forest.close()
}