package com.promotion.rule_engine.converter

import com.promotion.rule_engine.Constants
import com.promotion.rule_engine.model.Campaign
import org.joda.time.format.DateTimeFormat
import play.api.libs.json.{JsValue, Json, Writes}

/**
 * Created by sudan on 10/04/16.
 */
object CampaignConverter {

  def toJson(campaign: Campaign): JsValue = {

    val format = DateTimeFormat.forPattern(Constants.DISPLAY_FORMAT)
    implicit val campaignWriter = new Writes[Campaign] {
      def writes(campaign: Campaign) = Json.obj(
        Constants.CAMPAIGN_RULE_IDS -> Json.toJsFieldJsValueWrapper(campaign.ruleIds),
        Constants.CAMPAIGN_START_DATE -> format.print(campaign.startDate),
        Constants.VERTICALS -> format.print(campaign.endDate)
      )
    }
    Json.toJson(campaign)
  }
}
