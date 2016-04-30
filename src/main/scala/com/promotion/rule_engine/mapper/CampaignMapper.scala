package com.promotion.rule_engine.mapper

import com.mongodb.{BasicDBObject, BasicDBList, DBObject}
import com.promotion.rule_engine.Constants
import com.promotion.rule_engine.model.{RuleRelationship, Campaign}

import scala.collection.mutable.ArrayBuffer

/**
 * Created by sudan on 10/04/16.
 */
object CampaignMapper {

  def map(dBObject: DBObject): Campaign = {
    val id = dBObject.get(Constants.CAMPAIGN_ID).asInstanceOf[String]
    val ruleIds = dBObject.get(Constants.CAMPAIGN_RULE_IDS).asInstanceOf[BasicDBList].toArray.map(_.toString)
    val startDate = dBObject.get(Constants.CAMPAIGN_START_DATE).asInstanceOf[Long]
    val endDate = dBObject.get(Constants.CAMPAIGN_END_DATE).asInstanceOf[Long]
    val ruleRelationshipList = dBObject.get(Constants.CAMPAIGN_RULE_RELATIONSHIPS).asInstanceOf[BasicDBList]
    val ruleRelationships = ArrayBuffer[RuleRelationship]()
    for (i <- 0 until ruleRelationshipList.size) {
      val ruleRelationshipObj = ruleRelationshipList.get(i).asInstanceOf[BasicDBObject]
      val firstRuleId = ruleRelationshipObj.get(Constants.CAMPAIGN_FIRST_RULE_ID).asInstanceOf[String]
      val secondRuleId = ruleRelationshipObj.get(Constants.CAMPAIGN_SECOND_RULE_ID).asInstanceOf[String]
      val operation = ruleRelationshipObj.get(Constants.RULE_OPERATION).asInstanceOf[String]
      val ruleRelationship = RuleRelationship(firstRuleId, secondRuleId, operation)
      ruleRelationships += ruleRelationship
    }

    Campaign(id, ruleIds, startDate, endDate, ruleRelationships.toArray)
  }
}
