package com.promotion.rule_engine.mapper

import com.mongodb.{BasicDBList, DBObject}
import com.promotion.rule_engine.Constants
import com.promotion.rule_engine.model.RegionList

/**
 * Created by sudan on 09/04/16.
 */
object RegionListMapper {

  def map(dBObject: DBObject): RegionList = {
    val regionList = dBObject.get(Constants.REGION_LIST).asInstanceOf[DBObject]
    val countries = regionList.get(Constants.COUNTRIES).asInstanceOf[BasicDBList].toArray.map(_.toString)
    val states = regionList.get(Constants.STATES).asInstanceOf[BasicDBList].toArray.map(_.toString)
    val cities = regionList.get(Constants.CITIES).asInstanceOf[BasicDBList].toArray.map(_.toString)
    val areas = regionList.get(Constants.AREAS).asInstanceOf[BasicDBList].toArray.map(_.toString)
    val pincodes = regionList.get(Constants.PINCODES).asInstanceOf[BasicDBList].toArray.map(_.toString)
    RegionList(countries, states, cities, areas, pincodes)
  }
}
