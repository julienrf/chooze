import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "chooze"
    val appVersion      = "1.0"

    val appDependencies = Seq(
      "org.scalaquery" % "scalaquery_2.9.1-1" % "0.10.0-M1"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here      
    )

}
