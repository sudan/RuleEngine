package com.promotion.rule_engine.dao.impl

import com.promotion.rule_engine.Constants
import com.promotion.rule_engine.bootstrap.RedisClient
import com.promotion.rule_engine.dao.api.DiscountDao
import com.promotion.rule_engine.model.{Category, Region}

import scala.collection.mutable.SortedSet

/**
 * Created by sudan on 24/04/16.
 */
class DiscountDaoImpl extends DiscountDao {

  var redisClient = RedisClient.getConnection

  def getRuleIds(region: Region): SortedSet[String] = {
    var rules = getRuleIds(Constants.PINCODES, region.pincode)
    if (rules.size > 0) {
      return rules
    }
    rules = getRuleIds(Constants.AREAS, region.area)
    if (rules.size > 0) {
      return rules
    }
    rules = getRuleIds(Constants.CITIES, region.city)
    if (rules.size > 0) {
      return rules
    }

    rules = getRuleIds(Constants.STATES, region.state)
    if (rules.size > 0) {
      return rules
    }

    rules = getRuleIds(Constants.COUNTRIES, region.country)
    if (rules.size > 0) {
      return rules
    }
    SortedSet[String]()
  }

  def getRuleIds(category: Category): SortedSet[String] = {
    var rules = getRuleIds(Constants.PRODUCT_IDS, category.productId)
    if (rules.size > 0) {
      return rules
    }
    rules = getRuleIds(Constants.VERTICALS, category.vertical)
    if (rules.size > 0) {
      return rules
    }
    rules = getRuleIds(Constants.SUB_CATEGORIES, category.subCategory)
    if (rules.size > 0) {
      return rules
    }

    rules = getRuleIds(Constants.MAIN_CATEGORIES, category.mainCategory)
    if (rules.size > 0) {
      return rules
    }

    SortedSet[String]()
  }

  def getRuleIds(properties: Map[String, String]): SortedSet[String] = {

    val rules = SortedSet[String]()
    for ((key, value) <- properties) {
      val propertyRule = getRuleIds(key, value)
      if (propertyRule.size > 0) {
        rules ++= propertyRule
      }
    }
    rules
  }

  private[this] def getRuleIds(prefix: String, suffix: String): SortedSet[String] = {
    val rules = SortedSet[String]()
    var ruleSet = redisClient.smembers(prefix + Constants.SEPARATOR + suffix).get
    ruleSet.map(ruleIds => rules += ruleIds.get)
    rules
  }
}
