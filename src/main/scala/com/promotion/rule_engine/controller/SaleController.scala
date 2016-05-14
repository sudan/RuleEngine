package com.promotion.rule_engine.controller

import spray.routing.HttpService

/**
 * Created by sudan on 14/05/16.
 */
trait SaleController extends HttpService{

  val createSaleRoute =
    path("sales") {
      post {
        complete("Sale Create")
      }
    }

  val getSaleRoute =
    path("sales") {
      get {
        complete("Sale Get")
      }
    }

  val updateSaleRoute =
    path("sales") {
      put {
        complete("Sale Update")
      }
    }

  val deleteSaleRoute =
    path("sales") {
      delete {
        complete("Sale Delete")
      }
    }

  val startSaleRoute =
    path("sales/start") {
      post {
        complete("Sale Start")
      }
    }
}
