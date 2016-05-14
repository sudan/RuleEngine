package com.promotion.rule_engine.controller

import com.promotion.rule_engine.service.impl.DiscountServiceImpl
import play.api.libs.json.Json
import spray.routing.HttpService

/**
 * Created by sudan on 14/05/16.
 */
trait DiscountController extends HttpService{

  val discountService = new DiscountServiceImpl

  val getDiscountRoute = {
    post {
      path("discount") {
        entity(as[String]) { jsonStr =>
          val json = Json.parse(jsonStr)
          val discount = discountService.getDiscount(json)
          complete(discount.toString)
        }
      }
    }
  }
}
