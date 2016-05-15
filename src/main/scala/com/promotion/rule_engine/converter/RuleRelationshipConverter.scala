package com.promotion.rule_engine.converter

import com.promotion.rule_engine.model.RuleRelationship
import play.api.libs.json.Json.JsValueWrapper
import play.api.libs.json.{JsValue, Json}

import scala.collection.mutable.ArrayBuffer

/**
 * Created by sudan on 01/05/16.
 */
object RuleRelationshipConverter {

  def toJson(ruleRelationships: Array[RuleRelationship]): JsValueWrapper = {

    val ruleRels = ruleRelationships.map(ruleRelationship => ruleRelationship.firstRuleId + " " +
      ruleRelationship.operation + " " + ruleRelationship.secondRuleId)
    Json.toJsFieldJsValueWrapper(ruleRels)
  }

  def fromJson(json: JsValue): Array[RuleRelationship] = {

    val ruleRels = json.as[Array[String]]
    val ruleRelationships = ArrayBuffer[RuleRelationship]()
    for (ruleRel <- ruleRels) {
      val ruleRelArr = ruleRel.split(" ")
      val firstRuleId = ruleRelArr(0)
      val operation = ruleRelArr(1)
      val secondRuleId = ruleRelArr(2)
      val ruleRelationship = RuleRelationship(firstRuleId, secondRuleId, operation)
      ruleRelationships += ruleRelationship
    }
    ruleRelationships.toArray
  }
}
