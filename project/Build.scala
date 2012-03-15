import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "chooze"
    val appVersion      = "1.0"

    val appDependencies = Seq(
      "org.scalaquery" % "scalaquery_2.9.0-1" % "0.9.5",
      "postgresql" % "postgresql" % "9.1-901-1.jdbc4"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here      
    )

}
