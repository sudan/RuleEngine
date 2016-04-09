package com.promotion.rule_engine.builder

import com.mongodb.DBObject
import com.mongodb.casbah.commons.MongoDBObject
import com.promotion.rule_engine.model.Campaign
import com.promotion.rule_engine.Constants

/**
 * Created by sudan on 09/04/16.
 */
object CampaignBuilder {

  def build(campaign: Campaign, campaignId: String): DBObject = {
    val campaignBuilder = MongoDBObject.newBuilder
    campaignBuilder += Constants.CAMPAIGN_ID -> campaignId
    campaignBuilder += Constants.CAMPAIGN_RULE_IDS -> campaign.ruleIds
    campaignBuilder += Constants.CAMPAIGN_START_DATE -> campaign.startDate
    campaignBuilder += Constants.CAMPAIGN_END_DATE -> campaign.endDate
    campaignBuilder += Constants.SOFT_DELETED -> false
    campaignBuilder.result
  }
}
