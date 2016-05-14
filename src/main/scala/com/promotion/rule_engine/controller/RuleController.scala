package com.promotion.rule_engine.controller

import com.promotion.rule_engine.service.impl.RuleServiceImpl
import play.api.libs.json.Json
import spray.http.{MediaTypes, StatusCodes}
import spray.routing.HttpService

/**
 * Created by sudan on 14/05/16.
 */
trait RuleController extends HttpService {

  val ruleService = new RuleServiceImpl
  val createRuleRoute =
    post {
      path("rules") {
        entity(as[String]) { jsonStr =>
          val json = Json.parse(jsonStr)
          complete(ruleService.createRule(json))
        }
      }
    }

  val getRuleRoute =
    get {
      path("rules" / Segment) { ruleId: String =>
        ruleService.getRule(ruleId) match {
          case Left(e) => complete("Invalid ruleId " + ruleId)
          case Right(json) =>
            respondWithMediaType(MediaTypes.`application/json`) {
              complete(Json.stringify(json))
            }
        }
      }
    }

  val updateRuleRoute =
    put {
      path("rules") {
        entity(as[String]) { jsonStr =>
          val json = Json.parse(jsonStr)
          ruleService.updateRule(json) match {
            case Left(e) => complete("Invalid payload " + jsonStr)
            case Right(json) =>
              respondWithMediaType(MediaTypes.`application/json`) {
                complete(Json.stringify(json))
              }
          }
        }
      }
    }

  val deleteRuleRoute =
    delete {
      path("rules" / Segment) { ruleId: String =>
        ruleService.deleteRule(ruleId)
        complete(StatusCodes.NoContent)
      }
    }
}
