package com.promotion.rule_engine.service.api

import play.api.libs.json.JsValue

/**
 * Created by sudan on 10/04/16.
 */
trait RuleService {

  /**
   * Create a rule and return the id generated
   * @param json
   * @return
   */
  def createRule(json: JsValue): String
}
