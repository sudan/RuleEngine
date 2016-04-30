package com.promotion.rule_engine.builder

import com.mongodb.DBObject
import com.mongodb.casbah.commons.{MongoDBObject, MongoDBList}
import com.promotion.rule_engine.Constants
import com.promotion.rule_engine.model.Campaign

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
    val ruleRelationshipsBuilder = MongoDBList.newBuilder
    for (ruleRelationship <- campaign.ruleRelationships) {
      val ruleRelationBuilder = MongoDBObject.newBuilder
      ruleRelationBuilder += Constants.CAMPAIGN_FIRST_RULE_ID -> ruleRelationship.firstRuleId
      ruleRelationBuilder += Constants.CAMPAIGN_SECOND_RULE_ID -> ruleRelationship.secondRuleId
      ruleRelationBuilder += Constants.RULE_OPERATION -> ruleRelationship.operation
      ruleRelationshipsBuilder += ruleRelationBuilder.result
    }
    campaignBuilder += Constants.CAMPAIGN_RULE_RELATIONSHIPS -> ruleRelationshipsBuilder.result
    campaignBuilder.result
  }
}
