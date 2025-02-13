import Dependencies._

ThisBuild / scalaVersion := "2.13.1"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "apien"

lazy val root = (project in file("."))
  .settings(
    name := "bash-crawler",
    libraryDependencies += pureConfig,
    libraryDependencies += jsoup,
    libraryDependencies += cats,
    libraryDependencies += akkaHttp,
    libraryDependencies += akkaStream,
    libraryDependencies += akkHttpSprayJson,
    libraryDependencies += scalaLogging,
    libraryDependencies += logback,
    libraryDependencies += akkaTestKit % Test,
    libraryDependencies += scalaTest % Test,
  )
// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
