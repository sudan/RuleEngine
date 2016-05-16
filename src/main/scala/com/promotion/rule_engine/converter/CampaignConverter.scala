package com.promotion.rule_engine.converter

import com.promotion.rule_engine.Constants
import com.promotion.rule_engine.model.Campaign
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import play.api.libs.json._


/**
 * Created by sudan on 10/04/16.
 */
object CampaignConverter {

  def toJson(campaign: Campaign): JsValue = {

    val format = DateTimeFormat.forPattern(Constants.DATE_PATTERN)
    implicit val campaignWriter = new Writes[Campaign] {
      def writes(campaign: Campaign) = Json.obj(
        Constants.ID -> campaign.id,
        Constants.CAMPAIGN_RULE_IDS -> Json.toJsFieldJsValueWrapper(campaign.ruleIds),
        Constants.CAMPAIGN_START_DATE -> format.print(campaign.startDate),
        Constants.CAMPAIGN_END_DATE -> format.print(campaign.endDate),
        Constants.RULE_RELATIONSHIP -> RuleRelationshipConverter.toJson(campaign.ruleRelationships)
      )
    }
    Json.toJson(campaign)
  }

  def fromJson(json: JsValue): Campaign = {

    val idResult = (json \ Constants.ID).validate[String]
    val id = idResult match {
      case s: JsSuccess[String] => s.value
      case e: JsError => Constants.SENTINEL_ID
    }

    val ruleIds = (json \ Constants.CAMPAIGN_RULE_IDS).as[Array[String]]
    val format = DateTimeFormat.forPattern(Constants.DATE_PATTERN)
    val startDate = format.parseDateTime((json \ Constants.CAMPAIGN_START_DATE).as[String]).getMillis
    val endDate = format.parseDateTime((json \ Constants.CAMPAIGN_END_DATE).as[String]).getMillis
    val ruleRelationships = RuleRelationshipConverter.fromJson(json \ Constants.RULE_RELATIONSHIP)
    Campaign(id, ruleIds, startDate, endDate, ruleRelationships)
  }
}
