package com.promotion.rule_engine.converter

import com.promotion.rule_engine.Constants
import com.promotion.rule_engine.model.Sale
import org.joda.time.format.DateTimeFormat
import play.api.libs.json._

/**
 * Created by sudan on 10/04/16.
 */
object SaleConverter {


  def toJson(sale: Sale): JsValue = {

    val format = DateTimeFormat.forPattern(Constants.DISPLAY_FORMAT)
    implicit val saleWriter = new Writes[Sale] {
      def writes(sale: Sale) = Json.obj(
        Constants.SALE_CAMPAIGN_IDS -> Json.toJsFieldJsValueWrapper(sale.campaignIds)
      )
    }
    Json.toJson(sale)
  }

  def fromJson(json: JsValue): Sale = {

    val idResult = (json \ Constants.ID).validate[String]
    val id = idResult match {
      case s: JsSuccess[String] => idResult.asInstanceOf[String]
      case e: JsError => Constants.SENTINEL_ID
    }

    val campaignIds = (json \ Constants.SALE_CAMPAIGN_IDS).as[Array[String]]
    Sale(id, campaignIds)
  }

}
