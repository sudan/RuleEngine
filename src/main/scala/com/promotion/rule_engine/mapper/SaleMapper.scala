package com.promotion.rule_engine.mapper

import com.mongodb.{BasicDBList, DBObject}
import com.promotion.rule_engine.Constants
import com.promotion.rule_engine.model.Sale

/**
 * Created by sudan on 10/04/16.
 */
object SaleMapper {

  def map(dBObject: DBObject): Sale = {
    val id = dBObject.get(Constants.SALE_ID).asInstanceOf[String]
    val campaignIds = dBObject.get(Constants.SALE_CAMPAIGN_IDS).asInstanceOf[BasicDBList].toArray
      .map(_.toString)
    Sale(id, campaignIds)
  }
}
