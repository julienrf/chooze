// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository 
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// Use the Play sbt plugin for Play projects
addSbtPlugin("play" % "sbt-plugin" % "2.0-RC5" from "http://repo.typesafe.com/typesafe/releases/play/sbt-plugin/scala_2.9.1/sbt_0.11.2/2.0-RC5/jars/sbt-plugin.jar")
