import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "chooze"
    val appVersion      = "1.0"

    val appDependencies = Seq(
      "org.scalaquery" % "scalaquery_2.9.0-1" % "0.9.5",
      "mysql" % "mysql-connector-java" % "5.1.18"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here      
    )

}
