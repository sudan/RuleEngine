package com.promotion.rule_engine.converter

import com.promotion.rule_engine.Constants
import com.promotion.rule_engine.model.{Category, Region}
import play.api.libs.json.{Json, JsPath, Reads, JsValue}
import play.api.libs.functional.syntax._


/**
 * Created by sudan on 24/04/16.
 */
object CategoryConverter {

  def fromJson(json: JsValue): Category = {

    implicit val categoryReader: Reads[Category] = (
      (JsPath \ Constants.MAIN_CATEGORY).read[String] and
      (JsPath \ Constants.SUB_CATEGORY).read[String] and
      (JsPath \ Constants.VERTICAL).read[String] and
      (JsPath \ Constants.PRODUCT_ID).read[String]
    ) (Category.apply _)
    Json.fromJson(json \ Constants.CATEGORY).get
  }
}
