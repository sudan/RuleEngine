package com.promotion.rule_engine.converter

import com.promotion.rule_engine.Constants
import com.promotion.rule_engine.model.Region
import play.api.libs.json.{Json, JsPath, Reads, JsValue}
import play.api.libs.functional.syntax._

/**
 * Created by sudan on 24/04/16.
 */
object RegionConverter {

  def fromJson(json: JsValue): Region = {

    implicit val regionReader: Reads[Region] = (
      (JsPath \ Constants.COUNTRY).read[String] and
      (JsPath \ Constants.STATE).read[String] and
      (JsPath \ Constants.CITY).read[String] and
      (JsPath \ Constants.AREA).read[String] and
      (JsPath \ Constants.PINCODE).read[String]
    ) (Region.apply _)
    Json.fromJson(json \ Constants.REGION).get
  }
}
