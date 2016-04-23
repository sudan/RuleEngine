package com.promotion.rule_engine.dao.api

import com.promotion.rule_engine.model.Campaign

/**
 * Created by sudan on 09/04/16.
 */
trait CampaignDao {

  /**
   * Create a campaign and return the campaignId
   * @param campaign
   * @return
   */
  def insert(campaign: Campaign): String

  /**
   * Get the campaign given a campaignOd
   * @param campaignId
   * @return
   */
  def get(campaignId: String): Either[Throwable, Campaign]

  /**
   * Update the campaign and return the campaign object updated
   * @param campaign
   * @return
   */
  def update(campaign: Campaign): Either[Throwable, Campaign]

  /**
   * Soft delete the campaign given the campaignId
   * @param campaignId
   */
  def delete(campaignId: String): Unit
}
