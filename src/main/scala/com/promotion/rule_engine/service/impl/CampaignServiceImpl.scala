package com.promotion.rule_engine.service.impl

import com.promotion.rule_engine.converter.CampaignConverter
import com.promotion.rule_engine.dao.impl.CampaignDaoImpl
import com.promotion.rule_engine.service.api.CampaignService
import play.api.libs.json.JsValue

/**
 * Created by sudan on 23/04/16.
 */
class CampaignServiceImpl extends CampaignService {

  val campaignDao = new CampaignDaoImpl

  def createCampaign(json: JsValue): String = {
    val campaign = CampaignConverter.fromJson(json)
    campaignDao.insert(campaign)
  }

  def getCampaign(campaignId: String): Either[Throwable, JsValue] = {
    val campaign = campaignDao.get(campaignId)
    campaign match {
      case Left(exception) => throw exception
      case Right(campaign) => Right(CampaignConverter.toJson(campaign))
    }
  }

  def updateCampaign(json: JsValue): Either[Throwable, JsValue] = {
    val campaign = CampaignConverter.fromJson(json)
    campaignDao.update(campaign) match {
      case Left(exception) => Left(throw exception)
      case Right(campaign) => Right(CampaignConverter.toJson(campaign))
    }
  }


  def deleteCampaign(campaignId: String): Unit = {
    campaignDao.delete(campaignId)
  }
}
