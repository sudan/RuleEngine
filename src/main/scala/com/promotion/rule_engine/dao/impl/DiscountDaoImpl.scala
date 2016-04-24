package com.promotion.rule_engine.dao.impl

import com.promotion.rule_engine.Constants
import com.promotion.rule_engine.bootstrap.RedisClient
import com.promotion.rule_engine.dao.api.DiscountDao
import com.promotion.rule_engine.model.{Category, Region}
import scala.collection.mutable.Set

import scala.collection.mutable.SortedSet

/**
 * Created by sudan on 24/04/16.
 */
class DiscountDaoImpl extends DiscountDao {

  var redisClient = RedisClient.getConnection

  def getRuleIds(region: Region): Set[String] = {
    var rules = getRuleIds(Constants.PINCODE, region.pincode)
    if (rules.size > 0) {
      return rules
    }
    rules = getRuleIds(Constants.AREA, region.area)
    if (rules.size > 0) {
      return rules
    }
    rules = getRuleIds(Constants.CITY, region.city)
    if (rules.size > 0) {
      return rules
    }

    rules = getRuleIds(Constants.STATE, region.state)
    if (rules.size > 0) {
      return rules
    }

    rules = getRuleIds(Constants.COUNTRY, region.country)
    if (rules.size > 0) {
      return rules
    }
    Set.empty[String]
  }

  def getRuleIds(category: Category): Set[String] = {
    var rules = getRuleIds(Constants.PRODUCT_ID, category.productId)
    if (rules.size > 0) {
      return rules
    }
    rules = getRuleIds(Constants.VERTICAL, category.vertical)
    if (rules.size > 0) {
      return rules
    }
    rules = getRuleIds(Constants.SUB_CATEGORY, category.subCategory)
    if (rules.size > 0) {
      return rules
    }

    rules = getRuleIds(Constants.MAIN_CATEGORY, category.mainCategory)
    if (rules.size > 0) {
      return rules
    }

    Set.empty[String]
  }

  def getRuleIds(properties: Map[String, String]): Set[String] = {

    val rules = Set.empty[String]
    for ((key, value) <- properties) {
      val propertyRule = getRuleIds(key, value)
      if (propertyRule.size > 0) {
        rules ++= propertyRule
      }
    }
    rules
  }

  private[this] def getRuleIds(prefix: String, suffix: String): Set[String] = {
    val rules = SortedSet[String]()
    var ruleSet = redisClient.smembers(prefix + Constants.SEPARATOR + suffix).get
    ruleSet.map(ruleIds => rules += ruleIds.get)
    rules
  }
}
