package com.promotion.rule_engine.service.impl

import com.promotion.rule_engine.converter.{ProductAttributeConverter, CategoryConverter, RegionConverter}
import com.promotion.rule_engine.dao.impl.DiscountDaoImpl
import com.promotion.rule_engine.service.api.DiscountService
import play.api.libs.json.JsValue

/**
 * Created by sudan on 24/04/16.
 */
class DiscountServiceImpl extends DiscountService {

  val discountDao = new DiscountDaoImpl

  def getDiscount(json: JsValue): Double = {
    val region = RegionConverter.fromJson(json)
    val category = CategoryConverter.fromJson(json)
    val properties = ProductAttributeConverter.fromJson(json)

    var regionRuleIds = discountDao.getRuleIds(region)
    val categoryRuleIds = discountDao.getRuleIds(category)
    val propertyRuleIds = discountDao.getRuleIds(properties)

    return 1.2

  }
}
