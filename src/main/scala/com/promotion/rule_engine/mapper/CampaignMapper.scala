package com.promotion.rule_engine.mapper

import com.mongodb.{BasicDBList, DBObject}
import com.promotion.rule_engine.Constants
import com.promotion.rule_engine.model.Campaign

/**
 * Created by sudan on 10/04/16.
 */
object CampaignMapper {

  def map(dBObject: DBObject): Campaign = {
    val id = dBObject.get(Constants.CAMPAIGN_ID).asInstanceOf[String]
    val ruleIds = dBObject.get(Constants.CAMPAIGN_RULE_IDS).asInstanceOf[BasicDBList].toArray.map(_.toString)
    val startDate = dBObject.get(Constants.CAMPAIGN_START_DATE).asInstanceOf[Long]
    val endDate = dBObject.get(Constants.CAMPAIGN_END_DATE).asInstanceOf[Long]
    Campaign(id, ruleIds, startDate, endDate)
  }
}
