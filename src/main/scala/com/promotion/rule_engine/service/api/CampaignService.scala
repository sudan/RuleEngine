package com.promotion.rule_engine.service.api

import play.api.libs.json.JsValue

/**
 * Created by sudan on 23/04/16.
 */
trait CampaignService {

  /**
   * Create a new campaign and return the campaign ID
   * @param json
   * @return
   */
  def createCampaign(json: JsValue): String

  /**
   * Get the campaign given the campaign ID
   * @param campaignId
   * @return
   */
  def getCampaign(campaignId: String): Either[Throwable, JsValue]

  /**
   * Update the campaign and return the updated campaign
   * @param json
   * @return
   */
  def updateCampaign(json: JsValue): Either[Throwable, JsValue]

  /**
   * Delete the campaign given the campaignID
   * @param campaignId
   */
  def deleteCampaign(campaignId: String): Unit
}
