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

  /**
   * Get the rule given the ruleId
   * @param ruleId
   * @return
   */
  def getRule(ruleId: String): Either[Throwable, JsValue]

  /**
   * Update the rule and return the updated rule
   * @param json
   * @return
   */
  def updateRule(json: JsValue): Either[Throwable, JsValue]

  /**
   * Delete the rule given the ruleId
   * @param ruleId
   */
  def deleteRule(ruleId: String): Unit
}
