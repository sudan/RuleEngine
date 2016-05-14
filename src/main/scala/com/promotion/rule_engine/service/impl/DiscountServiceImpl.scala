package com.promotion.rule_engine.service.impl

import com.promotion.rule_engine.Constants
import com.promotion.rule_engine.converter.{CategoryConverter, ProductAttributeConverter, RegionConverter}
import com.promotion.rule_engine.dao.impl.{DiscountDaoImpl, RuleDaoImpl}
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
    var regionRuleIds = discountDao.getRuleIds(RegionConverter.fromJson(json))
    val categoryRuleIds = discountDao.getRuleIds(CategoryConverter.fromJson(json))
    val propertyRuleIds = discountDao.getRuleIds(ProductAttributeConverter.fromJson(json))

    val ruleIds = regionRuleIds.intersect(categoryRuleIds).intersect(propertyRuleIds)

    var discount = 0.0
    var prevBoost = Constants.SENTINEL_BOOST
    var prevRuleId = Constants.SENTINEL_ID
    val ruleRelationshipMap = ruleDao.getRuleRelationships

    for (ruleId <- ruleIds) {
      val discountAttrs = ruleDao.getDiscountedAttrs(ruleId)
      if (prevRuleId == Constants.SENTINEL_ID) {
        discount = discountAttrs(Constants.DISCOUNT).toDouble
        var currentBoost = discountAttrs(Constants.RULE_BOOST).toInt
        prevBoost = currentBoost
        prevRuleId = ruleId
      } else {
        var currentBoost = discountAttrs(Constants.RULE_BOOST).toInt
        if (hasRelationship(ruleId, prevRuleId, ruleRelationshipMap)) {
          discount = getCompositeDiscount(ruleId, prevRuleId, discountAttrs(Constants.DISCOUNT).toDouble, discount, ruleRelationshipMap)
        } else {
          if (currentBoost == prevBoost) {
            discount = Math.max(discount, discountAttrs(Constants.DISCOUNT).toDouble)
          }
          else if (currentBoost > prevBoost) {
            prevBoost = currentBoost
            prevRuleId = ruleId
            discount = discountAttrs(Constants.DISCOUNT).toDouble
          }
        }
      }
    }
    return discount

  }

  private[this] def hasRelationship(currentRuleId: String, prevRuleId: String, ruleRelationshipMap: Map[String, String]): Boolean = {
    val key = currentRuleId + Constants.SEPARATOR + prevRuleId
    val inverseKey = prevRuleId + Constants.SEPARATOR + currentRuleId
    ruleRelationshipMap.exists(_._1 == key) || ruleRelationshipMap.exists(_._1 == inverseKey)
  }

  private[this] def getCompositeDiscount(currentRuleId: String, prevRuleId: String,
                                         currentDiscount: Double,
                                         prevDiscount: Double, ruleRelationshipMap: Map[String, String]): Double = {
    val key = currentRuleId + Constants.SEPARATOR + prevRuleId
    val inverseKey = prevRuleId + Constants.SEPARATOR + currentRuleId

    if (ruleRelationshipMap.exists(_._1 == key)) {
      val operation = ruleRelationshipMap.get(key)
      operation match {
        case Some(Constants.OP_AND) => currentDiscount + prevDiscount
        case _ => 0
      }
    } else {
      val operation = ruleRelationshipMap.get(inverseKey)
      operation match {
        case Some(Constants.OP_AND) => currentDiscount + prevDiscount
        case _ => 0
      }
    }
  }
}
