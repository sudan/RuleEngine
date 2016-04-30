package com.promotion.rule_engine.service.impl

import com.promotion.rule_engine.Constants
import com.promotion.rule_engine.converter.{ProductAttributeConverter, CategoryConverter, RegionConverter}
import com.promotion.rule_engine.dao.impl.{RuleDaoImpl, DiscountDaoImpl}
import com.promotion.rule_engine.service.api.DiscountService
import play.api.libs.json.JsValue
import scala.collection.mutable.Set

/**
 * Created by sudan on 24/04/16.
 */
class DiscountServiceImpl extends DiscountService {

  val discountDao = new DiscountDaoImpl
  val ruleDao = new RuleDaoImpl

  def getDiscount(json: JsValue): Double = {
    val region = RegionConverter.fromJson(json)
    val category = CategoryConverter.fromJson(json)
    val properties = ProductAttributeConverter.fromJson(json)

    var regionRuleIds = discountDao.getRuleIds(region)
    val categoryRuleIds = discountDao.getRuleIds(category)
    val propertyRuleIds = discountDao.getRuleIds(properties)

    var ruleIds = Set.empty[String]
    if (regionRuleIds.isEmpty || categoryRuleIds.isEmpty) {
        ruleIds = regionRuleIds.union(categoryRuleIds)
    } else {
      ruleIds = regionRuleIds.intersect(categoryRuleIds)
    }

    if (!propertyRuleIds.isEmpty) {
      ruleIds = ruleIds.intersect(propertyRuleIds)
    }

    var discount = 0.0
    var prevBoost = Constants.SENTINEL_BOOST
    for (ruleId <- ruleIds) {
      val discountAttrs = ruleDao.getDiscountedAttrs(ruleId)
      if (prevBoost == Constants.SENTINEL_BOOST) {
        discount = discountAttrs(Constants.DISCOUNT).toDouble
        var currentBoost = discountAttrs(Constants.RULE_BOOST).toInt
        prevBoost = currentBoost
      } else {
        var currentBoost = discountAttrs(Constants.RULE_BOOST).toInt
        if (currentBoost == prevBoost) {
          discount = Math.max(discount, discountAttrs(Constants.DISCOUNT).toDouble)
        }
        else if (currentBoost > prevBoost) {
          prevBoost = currentBoost
          discount = discountAttrs(Constants.DISCOUNT).toDouble
        }
      }
    }
    return discount

  }
}
