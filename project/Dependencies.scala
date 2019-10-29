import sbt._

object Dependencies {
  private val akkaVersion = "2.5.26"
  private val akkaHttpVersion = "10.1.10"

  lazy val pureConfig = "com.github.pureconfig" %% "pureconfig" % "0.12.1"
  lazy val jsoup = "org.jsoup" % "jsoup" % "1.12.1"
  lazy val cats = "org.typelevel" %% "cats-core" % "2.0.0"
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.8"
  lazy val akkaHttp = "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
  lazy val akkaStream = "com.typesafe.akka" %% "akka-stream" % akkaVersion
  lazy val akkaTestKit = "com.typesafe.akka" %% "akka-testkit" % akkaVersion
  lazy val akkHttpSprayJson = "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion
}
