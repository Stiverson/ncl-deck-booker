name := "ncl-deck-booker-backend"

version := "0.1"

scalaVersion := "2.13.12"

val AkkaVersion = "2.8.0"
val AkkaHttpVersion = "10.5.0"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.11",
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion
)