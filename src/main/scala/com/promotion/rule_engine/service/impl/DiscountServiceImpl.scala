package com.promotion.rule_engine.service.impl

import com.promotion.rule_engine.service.api.DiscountService
import play.api.libs.json.JsValue

/**
 * Created by sudan on 24/04/16.
 */
class DiscountServiceImpl extends DiscountService {


  def getDiscount(json: JsValue): Double = {
    return 1.2

  }
}
