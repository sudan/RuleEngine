name := "Rule Engine"

version := "0.0.1"

scalaVersion := "2.11.5"

description := "A generalized promotion rule engine for products which allows to define rules for discounts"

scalacOptions := Seq("-feature", "-unchecked", "-deprecation", "-encoding", "utf8")

val redisVersion = "3.0"
val slf4jVersion = "1.7.12"
val akkaVersion = "2.3.9"
val mongodbVersion = "2.7.3"
val playJsonVersion = "2.3.4"

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"


libraryDependencies ++= Seq(
  "net.debasishg" %% "redisclient" % redisVersion,
  "org.slf4j" % "slf4j-api" % slf4jVersion,
  "org.mongodb" %% "casbah" % mongodbVersion,
  "com.typesafe.play" %% "play-json" % playJsonVersion
)
