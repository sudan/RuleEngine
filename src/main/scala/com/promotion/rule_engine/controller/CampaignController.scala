package com.promotion.rule_engine.controller

import com.promotion.rule_engine.service.impl.CampaignServiceImpl
import play.api.libs.json.Json
import spray.http.{MediaTypes, StatusCodes}
import spray.routing.HttpService

/**
 * Created by sudan on 14/05/16.
 */
trait CampaignController extends HttpService {

  val campaignService = new CampaignServiceImpl

  val createCampaignRoute =
    post {
      path("campaigns") {
        entity(as[String]) { jsonStr =>
          val json = Json.parse(jsonStr)
          complete(campaignService.createCampaign(json))
        }
      }
    }

  val getCampaignRoute =
    get {
      path("campaigns" / Segment) { campaignId: String =>
        campaignService.getCampaign(campaignId) match {
          case Left(e) => complete("Invalid campaignId " + campaignId)
          case Right(json) =>
            respondWithMediaType(MediaTypes.`application/json`) {
              complete(Json.stringify(json))
            }
        }
      }
    }

  val updateCampaignRoute =
    put {
      path("campaigns") {
        entity(as[String]) { jsonStr =>
          val json = Json.parse(jsonStr)
          campaignService.updateCampaign(json) match {
            case Left(e) => complete("Invalid payload " + jsonStr)
            case Right(json) =>
              respondWithMediaType(MediaTypes.`application/json`) {
                complete(Json.stringify(json))
              }
          }
        }
      }
    }

  val deleteCampaignRoute =
    delete {
      path("campaigns" / Segment) { campaignId: String =>
        campaignService.deleteCampaign(campaignId)
        complete(StatusCodes.NoContent)
      }
    }
}
