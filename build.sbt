name := "Rule Engine"

version := "0.0.1"

description := "A generalized promotion rule engine for products allowing to define rules for discounts"

scalacOptions := Seq("-feature", "-unchecked", "-deprecation", "-encoding", "utf8")

val redisVersion = "3.0"
val slf4jVersion = "1.7.12"
val akkaVersion  = "2.3.9"
val mongodbVersion = "2.7.3"

libraryDependencies ++= Seq(
	
	"net.debasishg" %% "redisclient" % redisVersion,
	"org.slf4j" % "slf4j-api" % slf4jVersion,
	"com.typesafe.akka" % "akka-actor" % akkaVersion,
	"com.typesafe.akka" % "akka-slf4j" % akkaVersion,
	"org.mongodb" %% "casbah" % mongodbVersion
)