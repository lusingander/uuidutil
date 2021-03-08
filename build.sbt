ThisBuild / organization := "com.github.lusingander.uuid"
ThisBuild / scalaVersion := "2.12.13"

lazy val root = (project in file("."))
  .enablePlugins(NativeImagePlugin)
  .settings(
    name := "uuidutil",
    version := "0.0.1",
    nativeImageOptions ++= List(
      // https://github.com/oracle/graal/issues/712
      "--initialize-at-run-time=com.fasterxml.uuid.impl.RandomBasedGenerator$LazyRandom",
      "--initialize-at-build-time",
      "--no-fallback",
      "--no-server"
    ),
    nativeImageJvm := "graalvm",
    libraryDependencies ++= Seq(
      "com.github.scopt" %% "scopt" % "3.7.1",
      "com.lihaoyi" %% "ujson" % "1.1.0",
      "com.softwaremill.sttp.client" %% "core" % "2.2.1",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "org.scalatest" %% "scalatest" % "3.0.8" % Test,
      "com.fasterxml.uuid" % "java-uuid-generator" % "4.0.1"
    ),
    Compile / mainClass := Some("com.github.lusingander.uuid.Main")
  )
