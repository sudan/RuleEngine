package com.promotion.rule_engine.dao.impl

import com.mongodb.casbah.commons.MongoDBObject
import com.promotion.rule_engine.Constants
import com.promotion.rule_engine.bootstrap.{MongoClient, RedisClient => RedisCustomClient}
import com.promotion.rule_engine.builder.SaleBuilder
import com.promotion.rule_engine.dao.api.SaleDao
import com.promotion.rule_engine.generator.IdGenerator
import com.promotion.rule_engine.mapper.SaleMapper
import com.promotion.rule_engine.model.{RuleExpiry, Rule, RuleRelationship, Sale}

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

  def applyRules(rules: Array[Rule], ruleRelationships: Array[RuleRelationship], ruleExpiries: Array[RuleExpiry]): Unit = {

    redisClient.flushall
    redisClient.pipeline { client =>

      for (rule <- rules) {
        val ruleId = rule.id

        val countries = rule.regionList.countries
        val states = rule.regionList.states
        val cities = rule.regionList.cities
        val areas = rule.regionList.areas
        val pincodes = rule.regionList.pincodes

        val mainCategories = rule.categoryList.mainCategories
        val subCategories = rule.categoryList.subCategories
        val verticals = rule.categoryList.verticals
        val productIds = rule.categoryList.productIds

        var prefix = ""
        if (rule.isGlobal) {
          prefix = Constants.GLOBAL + Constants.SEPARATOR
        }

        for (ruleExpiry <- ruleExpiries) {
          client.hset(Constants.RULE_EXPIRY + Constants.SEPARATOR + ruleExpiry.ruleId, Constants.CAMPAIGN_START_DATE, ruleExpiry.startDate)
          client.hset(Constants.RULE_EXPIRY + Constants.SEPARATOR + ruleExpiry.ruleId, Constants.CAMPAIGN_END_DATE, ruleExpiry.endDate)
        }

        if (!productIds.isEmpty) {
          productIds.foreach(v => client.sadd(Constants.GLOBAL + Constants.SEPARATOR + Constants.PRODUCT_ID + Constants.SEPARATOR + v, ruleId))
          productIds.foreach(v => client.sadd(Constants.PRODUCT_ID + Constants.SEPARATOR + v, ruleId))
        } else if (!verticals.isEmpty) {
          verticals.foreach(v => client.sadd(prefix + Constants.VERTICAL + Constants.SEPARATOR + v, ruleId))
        } else if (!subCategories.isEmpty) {
          subCategories.foreach(v => client.sadd(prefix + Constants.SUB_CATEGORY + Constants.SEPARATOR + v, ruleId))
        } else if (!mainCategories.isEmpty) {
          mainCategories.foreach(v => client.sadd(prefix + Constants.MAIN_CATEGORY + Constants.SEPARATOR + v, ruleId))
        }

        if (!pincodes.isEmpty) {
          pincodes.foreach(v => client.sadd(prefix + Constants.PINCODE + Constants.SEPARATOR + v, ruleId))
        } else if (!areas.isEmpty) {
          areas.foreach(v => client.sadd(prefix + Constants.AREA + Constants.SEPARATOR + v, ruleId))
        } else if (!cities.isEmpty) {
          cities.foreach(v => client.sadd(prefix + Constants.CITY + Constants.SEPARATOR + v, ruleId))
        } else if (!states.isEmpty) {
          states.foreach(v => client.sadd(prefix + Constants.STATE + Constants.SEPARATOR + v, ruleId))
        } else if (!countries.isEmpty) {
          countries.foreach(v => client.sadd(prefix + Constants.COUNTRY + Constants.SEPARATOR + v, ruleId))
        }

        for ((key, valueList) <- rule.properties) {
          for (value <- valueList) {
            client.sadd(key + Constants.SEPARATOR + value, ruleId)
          }
        }
        client.hset(Constants.RULE + Constants.SEPARATOR + ruleId, Constants.DISCOUNT, rule.discount)
        client.hset(Constants.RULE + Constants.SEPARATOR + ruleId, Constants.RULE_BOOST, rule.boost)
      }

      for (ruleRelationship <- ruleRelationships) {
        val key = ruleRelationship.firstRuleId + Constants.SEPARATOR + ruleRelationship.secondRuleId
        client.hset(Constants.RULE_RELATIONSHIP, key, ruleRelationship.operation)
      }
    }
    ruleDao.activate(rules)
  }
}
