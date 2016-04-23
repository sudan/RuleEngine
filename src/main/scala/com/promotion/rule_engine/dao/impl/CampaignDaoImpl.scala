package com.promotion.rule_engine.dao.impl

import com.mongodb.casbah.commons.MongoDBObject
import com.promotion.rule_engine.Constants
import com.promotion.rule_engine.bootstrap.MongoClient
import com.promotion.rule_engine.builder.CampaignBuilder
import com.promotion.rule_engine.dao.api.CampaignDao
import com.promotion.rule_engine.generator.IdGenerator
import com.promotion.rule_engine.mapper.CampaignMapper
import com.promotion.rule_engine.model.Campaign

/**
 * Created by sudan on 09/04/16.
 */
class CampaignDaoImpl extends CampaignDao {

  val mongoClient = MongoClient.getConnection

  def insert(campaign: Campaign): String = {
    val id = IdGenerator.generate(Constants.CAMPAIGN_ID_PREFIX, Constants.CAMPAIGN_ID_LENGTH)
    val campaignObj = CampaignBuilder.build(campaign, id)
    val collection = mongoClient(Constants.CAMPAIGN_COLLECTION)
    collection.insert(campaignObj)
    id
  }

  def get(campaignId: String): Either[Throwable, Campaign] = {
    val collection = mongoClient(Constants.CAMPAIGN_COLLECTION)
    val document = collection.findOne(MongoDBObject(Constants.CAMPAIGN_ID -> campaignId,
      Constants.SOFT_DELETED -> false))
    document match {
      case Some(_) =>
        Right(CampaignMapper.map(document.get))
      case None => Left(throw new Exception("Invalid campaignID " + campaignId))
    }
  }

  def update(campaign: Campaign): Either[Throwable, Campaign] = {
    val campaignObj = CampaignBuilder.build(campaign, campaign.id)
    val collection = mongoClient(Constants.CAMPAIGN_COLLECTION)
    val query = MongoDBObject(Constants.CAMPAIGN_ID -> campaign.id)
    collection.update(query, campaignObj)
    Right(campaign)
  }

  def delete(campaignId: String): Unit = {
    val query = MongoDBObject(Constants.CAMPAIGN_ID -> campaignId)
    val collection = mongoClient(Constants.CAMPAIGN_COLLECTION)
    collection.update(query, MongoDBObject(Constants.SET_OP ->
      MongoDBObject(Constants.SOFT_DELETED -> true)))
  }
}
