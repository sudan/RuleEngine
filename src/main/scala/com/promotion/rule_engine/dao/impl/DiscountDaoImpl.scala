package com.promotion.rule_engine.dao.impl

import com.promotion.rule_engine.Constants
import com.promotion.rule_engine.bootstrap.RedisClient
import com.promotion.rule_engine.dao.api.DiscountDao
import com.promotion.rule_engine.model.{Category, Region}

import scala.collection.mutable.Set

/**
 * Created by sudan on 24/04/16.
 */
class DiscountDaoImpl extends DiscountDao {

  var redisClient = RedisClient.getConnection

  def getRuleIds(region: Region, isGlobal: Boolean): Set[String] = {
    var prefix = ""
    if (isGlobal) {
      prefix = Constants.GLOBAL + Constants.SEPARATOR
    }
    var rules = getRuleIds(prefix + Constants.PINCODE, region.pincode)
    if (rules.size > 0) {
      return rules
    }
    rules = getRuleIds(prefix + Constants.AREA, region.area)
    if (rules.size > 0) {
      return rules
    }
    rules = getRuleIds(prefix + Constants.CITY, region.city)
    if (rules.size > 0) {
      return rules
    }

    rules = getRuleIds(prefix + Constants.STATE, region.state)
    if (rules.size > 0) {
      return rules
    }

    rules = getRuleIds(prefix + Constants.COUNTRY, region.country)
    if (rules.size > 0) {
      return rules
    }
    Set.empty[String]
  }

  def getRuleIds(category: Category, isGlobal: Boolean): Set[String] = {
    var prefix = ""
    if (isGlobal) {
      prefix = Constants.GLOBAL + Constants.SEPARATOR
    }

    var rules = getRuleIds(prefix + Constants.PRODUCT_ID, category.productId)
    if (rules.size > 0) {
      return rules
    }
    rules = getRuleIds(prefix + Constants.VERTICAL, category.vertical)
    if (rules.size > 0) {
      return rules
    }
    rules = getRuleIds(prefix + Constants.SUB_CATEGORY, category.subCategory)
    if (rules.size > 0) {
      return rules
    }

    rules = getRuleIds(prefix + Constants.MAIN_CATEGORY, category.mainCategory)
    if (rules.size > 0) {
      return rules
    }

    Set.empty[String]
  }

  private[this] def getRuleIds(prefix: String, suffix: String): Set[String] = {
    val rules = Set[String]()
    var ruleSet = redisClient.smembers(prefix + Constants.SEPARATOR + suffix).get
    ruleSet.map(ruleIds => rules += ruleIds.get)
    rules
  }

  def getRuleIds(properties: Map[String, String], isGlobal: Boolean): Set[String] = {

    var prefix = ""
    if (isGlobal) {
      prefix = Constants.GLOBAL + Constants.SEPARATOR
    }
    val rules = Set.empty[String]
    for ((key, value) <- properties) {
      val propertyRule = getRuleIds(prefix + key, value)
      if (propertyRule.size > 0) {
        rules ++= propertyRule
      }
    }
    rules
  }

  def filterActiveRuleIds(ruleIds: Set[String]): Set[String] = {
    val currentTime = System.currentTimeMillis
    val activeRules = Set[String]()
    for (ruleId <- ruleIds) {
      val ruleExpiryMap = redisClient.hgetall(Constants.RULE_EXPIRY + Constants.SEPARATOR + ruleId).get
      val startDate = ruleExpiryMap.get(Constants.CAMPAIGN_START_DATE).get.toLong
      val endDate =  ruleExpiryMap.get(Constants.CAMPAIGN_END_DATE).get.toLong
      if (currentTime > startDate && currentTime < endDate) {
        activeRules += ruleId
      }
    }
    activeRules
  }
}
