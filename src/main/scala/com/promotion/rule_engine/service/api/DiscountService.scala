package com.promotion.rule_engine.service.api

import play.api.libs.json.JsValue

/**
 * Created by sudan on 24/04/16.
 */
trait DiscountService {

  /**
   * Given the product json, fetch the appropriate discount applying rules
   * @param json
   * @return
   */
  def getDiscount(json: JsValue): Double
}
