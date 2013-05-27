import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName         = "chooze"
    val appVersion      = "1.0"

    val appDependencies = Seq(
      "com.typesafe.slick" %% "slick" % "1.0.0",
      "org.slf4j" % "slf4j-nop" % "1.6.4",
      "postgresql" % "postgresql" % "9.1-901-1.jdbc4",
      jdbc
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
      scalacOptions += "-feature"
    )

}
