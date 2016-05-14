package com.promotion.rule_engine

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import com.promotion.rule_engine.bootstrap.ConfigManager
import com.promotion.rule_engine.controller.RoutesActor
import spray.can.Http

import scala.concurrent.duration._


object RuleEngine extends App {
  ConfigManager.init
  val appHostName = ConfigManager.getValue("app.host")
  val appPort = ConfigManager.getValue("app.port").toInt

  implicit val system = ActorSystem("rule-engine")
  val service = system.actorOf(Props[RoutesActor], "rule-engine-service")
  implicit val timeout = Timeout(60.seconds)
  IO(Http) ? Http.Bind(service, interface = appHostName, port = appPort)
}
