import sbt._

object Dependencies {
  lazy val pureConfig = "com.github.pureconfig" %% "pureconfig" % "0.12.1"
  lazy val jsoup = "org.jsoup" % "jsoup" % "1.12.1"
  lazy val cats = "org.typelevel" %% "cats-core" % "2.0.0"
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.8"
}
