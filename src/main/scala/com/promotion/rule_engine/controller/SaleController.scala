package com.promotion.rule_engine.controller

import com.promotion.rule_engine.service.impl.SaleServiceImpl
import play.api.libs.json.Json
import spray.http.{MediaTypes, StatusCodes}
import spray.routing.HttpService

/**
 * Created by sudan on 14/05/16.
 */
trait SaleController extends HttpService {

  val saleService = new SaleServiceImpl

  val createSaleRoute =
    post {
      path("sales") {
        entity(as[String]) { jsonStr =>
          val json = Json.parse(jsonStr)
          complete(saleService.createSale(json))
        }
      }
    }

  val getSaleRoute =
    get {
      path("sales" / Segment) { saleId: String =>
        saleService.getSale(saleId) match {
          case Left(e) => complete("Invalid saleId " + saleId)
          case Right(json) =>
            respondWithMediaType(MediaTypes.`application/json`) {
              complete(Json.stringify(json))
            }
        }
      }
    }

  val updateSaleRoute =
    put {
      path("sales") {
        entity(as[String]) { jsonStr =>
          val json = Json.parse(jsonStr)
          saleService.updateSale(json) match {
            case Left(e) => complete("Invalid payload " + jsonStr)
            case Right(json) =>
              respondWithMediaType(MediaTypes.`application/json`) {
                complete(Json.stringify(json))
              }
          }
        }
      }
    }

  val deleteSaleRoute =
    delete {
      path("sales" / Segment) { saleId: String =>
        saleService.deleteSale(saleId)
        complete(StatusCodes.NoContent)
      }
    }

  val startSaleRoute =
    post {
      path("sales" / Segment / "start") { saleId: String =>
        saleService.startSale(saleId)
        complete("Sale Computation has been initiated")
      }
    }
}
