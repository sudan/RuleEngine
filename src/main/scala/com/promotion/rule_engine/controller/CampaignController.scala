package com.promotion.rule_engine.controller

import spray.routing.HttpService

/**
 * Created by sudan on 14/05/16.
 */
trait CampaignController extends HttpService{

  val createCampaignRoute =
    path("campaigns") {
      post {
        complete("Campaign Create")
      }
    }

  val getCampaignRoute =
    path("campaigns") {
      get {
        complete("Campaign Get")
      }
    }

  val updateCampaignRoute =
    path("campaigns") {
      get {
        complete("Campaign Update")
      }
    }

  val deleteCampaignRoute =
    path("campaigns") {
      delete {
        complete("Campaign Delete")
      }
    }
}
