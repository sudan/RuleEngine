package com.promotion.rule_engine.builder

import com.mongodb.DBObject
import com.mongodb.casbah.commons.MongoDBObject
import com.promotion.rule_engine.Constants
import com.promotion.rule_engine.model.Sale

/**
 * Created by sudan on 10/04/16.
 */
object SaleBuilder {

  def build(sale: Sale, saleId: String): DBObject = {
    val saleBuilder = MongoDBObject.newBuilder
    saleBuilder += Constants.SALE_ID -> saleId
    saleBuilder += Constants.SALE_CAMPAIGN_IDS -> sale.campaignIds
    saleBuilder += Constants.SOFT_DELETED -> false
    saleBuilder.result
  }
}
