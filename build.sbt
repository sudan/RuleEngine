name := "Rule Engine"

version := "0.0.1"

scalaVersion := "2.11.5"

description := "A generalized promotion rule engine for products which allows to define rules for discounts"

scalacOptions := Seq("-feature", "-unchecked", "-deprecation", "-encoding", "utf8")

val redisVersion =  "3.0"
val slf4jVersion = "1.7.12"
val akkaVersion = "2.3.9"
val mongodbVersion = "2.7.3"

libraryDependencies ++= Seq(
    "net.debasishg" %% "redisclient" % redisVersion,
    "org.slf4j" % "slf4j-api" % slf4jVersion,
    "org.mongodb" %% "casbah" % mongodbVersion
)