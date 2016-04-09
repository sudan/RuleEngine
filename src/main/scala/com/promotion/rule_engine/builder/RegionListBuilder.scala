package com.promotion.rule_engine.builder

import com.mongodb.casbah.Imports._
import com.promotion.rule_engine.Constants
import com.promotion.rule_engine.model.RegionList

/**
 * Created by sudan on 8/4/16.
 */
object RegionListBuilder {

  def build(regionList: RegionList): DBObject = {
    val regionListBuilder = MongoDBObject.newBuilder
    regionListBuilder += Constants.COUNTRIES -> regionList.countries
    regionListBuilder += Constants.STATES -> regionList.states
    regionListBuilder += Constants.CITIES -> regionList.cities
    regionListBuilder += Constants.AREAS -> regionList.areas
    regionListBuilder += Constants.PINCODES -> regionList.pincodes

    regionListBuilder.result
  }
}
