import sbt._

object Dependencies {
  lazy val pureConfig = "com.github.pureconfig" %% "pureconfig" % "0.12.1"
  lazy val jsoup = "org.jsoup" % "jsoup" % "1.12.1"
  lazy val cats = "org.typelevel" %% "cats-core" % "2.0.0"
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.8"
  lazy val akkaHttp = "com.typesafe.akka" %% "akka-http" % "10.1.10"
  lazy val akkaStream = "com.typesafe.akka" %% "akka-stream" % "2.5.26"
  lazy val akkaTestKit = "com.typesafe.akka" %% "akka-testkit" % "2.5.26"
}
