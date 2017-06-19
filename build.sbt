scalaVersion := "2.12.1"

name := "invoices-rest-api"

organization := "io.github.augustoerico"

version := "1.0"

libraryDependencies += "io.vertx" %% "vertx-lang-scala" % "3.4.1"
libraryDependencies += "io.vertx" % "vertx-web" % "3.4.2"
libraryDependencies += "io.vertx" % "vertx-mongo-client" % "3.4.2"
libraryDependencies += "org.json4s" %% "json4s-jackson" % "3.5.2"
