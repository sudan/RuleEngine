package com.promotion.rule_engine.controller

import akka.actor.Actor
import spray.routing.{HttpService, RequestContext}

/**
 * Created by sudan on 14/05/16.
 */
trait RuleController extends HttpService {

  val createRuleRoute =
    path("rules") {
      post {
          complete("Rule Create")
      }
    }

  val getRuleRoute =
    path("rules") {
      get {
          complete("Rule Get")
      }
    }

  val updateRuleRoute =
    path("rules") {
      put {
          complete("Rule Update")
      }
    }

  val deleteRuleRoute =
    path("rules") {
      delete {
          complete("Rule Delete")
      }
    }
}
