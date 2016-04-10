package com.promotion.rule_engine.converter

import com.promotion.rule_engine.Constants
import com.promotion.rule_engine.model.RegionList
import play.api.libs.json.{JsValue, Json, Writes}


/**
 * Created by sudan on 10/04/16.
 */
object RegionListConverter {

  def toJson(regionList: RegionList): JsValue = {

    implicit val regionListWriter = new Writes[RegionList] {
      def writes(regionList: RegionList) = Json.obj(
        Constants.COUNTRIES -> regionList.countries,
        Constants.STATES -> regionList.states,
        Constants.CITIES -> regionList.cities,
        Constants.AREAS -> regionList.areas,
        Constants.PINCODES -> regionList.pincodes
      )
    }
    Json.toJson(regionList)
  }
}
