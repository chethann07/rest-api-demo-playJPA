name := """rest-api-demo-playJPA"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.16"

libraryDependencies += guice



libraryDependencies ++= Seq(
  "org.hibernate" % "hibernate-core" % "6.6.1.Final",
  "jakarta.persistence" % "jakarta.persistence-api" % "3.1.0",
  "org.postgresql" % "postgresql" % "42.7.1",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.14.3"
)
//
//libraryDependencies += "org.playframework" %% "play-jpa" % "2.8.0"