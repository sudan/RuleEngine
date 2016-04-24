package com.promotion.rule_engine.dao.impl

import com.mongodb.casbah.commons.MongoDBObject
import com.promotion.rule_engine.Constants
import com.promotion.rule_engine.bootstrap.{MongoClient, RedisClient => RedisCustomClient}
import com.promotion.rule_engine.builder.SaleBuilder
import com.promotion.rule_engine.dao.api.SaleDao
import com.promotion.rule_engine.generator.IdGenerator
import com.promotion.rule_engine.mapper.SaleMapper
import com.promotion.rule_engine.model.{Rule, Sale}

/**
 * Created by sudan on 09/04/16.
 */
class SaleDaoImpl extends SaleDao {

  val mongoClient = MongoClient.getConnection
  val redisClient = RedisCustomClient.getConnection
  val ruleDao = new RuleDaoImpl

  def insert(sale: Sale): String = {
    val id = IdGenerator.generate(Constants.SALE_ID_PREFIX, Constants.SALE_ID_LENGTH)
    val saleObj = SaleBuilder.build(sale, id)
    val collection = mongoClient(Constants.SALE_COLLECTION)
    collection.insert(saleObj)
    id
  }

  def get(saleId: String): Either[Throwable, Sale] = {
    val collection = mongoClient(Constants.SALE_COLLECTION)
    val document = collection.findOne(MongoDBObject(Constants.SALE_ID -> saleId,
      Constants.SOFT_DELETED -> false))
    document match {
      case Some(_) =>
        Right(SaleMapper.map(document.get))
      case None => Left(throw new Exception("Invalid saleID " + saleId))
    }
  }

  def update(sale: Sale): Either[Throwable, Sale] = {
    val saleObj = SaleBuilder.build(sale, sale.id)
    val collection = mongoClient(Constants.SALE_COLLECTION)
    val query = MongoDBObject(Constants.SALE_ID -> sale.id)
    collection.update(query, saleObj)
    Right(sale)
  }

  def delete(saleId: String): Unit = {
    val query = MongoDBObject(Constants.SALE_ID -> saleId)
    val collection = mongoClient(Constants.SALE_COLLECTION)
    collection.update(query, MongoDBObject(Constants.SET_OP ->
      MongoDBObject(Constants.SOFT_DELETED -> true)))
  }

  def applyRules(rules: Array[Rule]): Unit = {

    redisClient.flushall
    redisClient.pipeline { client =>

      for (rule <- rules) {
        val ruleId = rule.id
        rule.regionList.countries.foreach(v => client.sadd(Constants.COUNTRY + Constants
          .SEPARATOR + v, ruleId))
        rule.regionList.states.foreach(v => client.sadd(Constants.STATE + Constants.SEPARATOR + v, ruleId))
        rule.regionList.cities.foreach(v => client.sadd(Constants.CITY + Constants.SEPARATOR + v,
          ruleId))
        rule.regionList.areas.foreach(v => client.sadd(Constants.AREA + Constants.SEPARATOR + v, ruleId))
        rule.regionList.pincodes.foreach(v => client.sadd(Constants.PINCODE + Constants.SEPARATOR + v, ruleId))

        rule.categoryList.mainCategories.foreach(v => client.sadd(Constants.MAIN_CATEGORY +
          Constants.SEPARATOR + v, ruleId))
        rule.categoryList.subCategories.foreach(v => client.sadd(Constants.SUB_CATEGORY +
          Constants.SEPARATOR + v, ruleId))
        rule.categoryList.verticals.foreach(v => client.sadd(Constants.VERTICAL + Constants.SEPARATOR + v, ruleId))
        rule.categoryList.productIds.foreach(v => client.sadd(Constants.PRODUCT_ID + Constants.SEPARATOR + v, ruleId))

        for ((key, valueList) <- rule.properties) {
          for (value <- valueList) {
            client.sadd(key + Constants.SEPARATOR + value, ruleId)
          }
        }
        client.set(Constants.DISCOUNT + Constants.SEPARATOR + ruleId, rule.discount)
      }
    }
    ruleDao.activate(rules)
  }
}
