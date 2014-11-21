name := """scala-backend-sampling"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

val springVersion = "4.1.1.RELEASE"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "postgresql" % "postgresql" % "9.1-901.jdbc4",
  "org.springframework" % "spring-context" % springVersion,
  "org.springframework" % "spring-aop" % springVersion,
  "org.springframework" % "spring-tx" % springVersion,
  "org.springframework" % "spring-jdbc" % springVersion,
  "com.nimbusds" % "nimbus-jose-jwt" % "2.25",
  "com.typesafe.slick" %% "slick" % "2.1.0",
  "org.slf4j" % "slf4j-nop" % "1.6.4"
)
