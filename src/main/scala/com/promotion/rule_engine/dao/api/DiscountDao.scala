package com.promotion.rule_engine.dao.api

import com.promotion.rule_engine.model.{Category, Region}

import scala.collection.mutable.Set

/**
 * Created by sudan on 24/04/16.
 */
trait DiscountDao {

  /**
   * Get ruleIds for the region
   * @param region
   * @return
   */
  def getRuleIds(region: Region, isGlobal: Boolean): Set[String]

  /**
   * Get ruleIds for the category
   * @param category
   * @return
   */
  def getRuleIds(category: Category, isGlobal: Boolean): Set[String]

  /**
   * Get ruleIds for properties
   * @param properties
   * @return
   */
  def getRuleIds(properties: Map[String, String], isGlobal: Boolean): Set[String]

  /**
   * Filter active rule Ids
   * @param ruleIds
   * @return
   */
  def filterActiveRuleIds(ruleIds: Set[String]): Set[String]
}
