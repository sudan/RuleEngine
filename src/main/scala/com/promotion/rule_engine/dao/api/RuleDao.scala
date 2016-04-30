package com.promotion.rule_engine.dao.api

import com.promotion.rule_engine.model.Rule

/**
 * Created by sudan on 8/4/16.
 */
trait RuleDao {

  /**
   * Persist a new rule and return the ruleId
   * @param rule
   * @return
   */
  def insert(rule: Rule): String

  /**
   * Get the rule object corresponding to ruleId
   * @param ruleId
   * @return
   */
  def get(ruleId: String): Either[Throwable, Rule]

  /**
   * Update the rule object and return the update rule object
   * @param rule
   * @return
   */
  def update(rule: Rule): Either[Throwable, Rule]

  /**
   * Soft delete the rule given ruleId
   * @param ruleId
   */
  def delete(ruleId: String): Unit

  /**
   * Activate all the rules
   * @param rules
   */
  def activate(rules: Array[Rule]): Unit

  /**
   * Get attributes specific to discounts
   */
  def getDiscountedAttrs(ruleId: String): Map[String, String]

  /**
   * Get rule relationships and their operations
   * @return
   */
  def getRuleRelationships(): Map[String, String]
}
