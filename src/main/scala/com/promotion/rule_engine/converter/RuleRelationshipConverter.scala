package com.promotion.rule_engine.converter

import com.promotion.rule_engine.Constants
import com.promotion.rule_engine.model.{RuleRelationship, Region}
import play.api.libs.json.{Json, JsPath, Reads, JsValue}

import scala.collection.mutable.ArrayBuffer

/**
 * Created by sudan on 01/05/16.
 */
object RuleRelationshipConverter {

  def toJson(ruleRelationships: Array[RuleRelationship]): JsValue = {

    val ruleRels = ruleRelationships.map(ruleRelationship => ruleRelationship.firstRuleId + " " +
      ruleRelationship.operation + " " + ruleRelationship.secondRuleId)
    Json.obj(
      Constants.RULE_RELATIONSHIP -> Json.toJsFieldJsValueWrapper(ruleRels)
    )
  }

  def fromJson(json: JsValue): Array[RuleRelationship] = {

    val ruleRels = json.as[Array[String]]
    val ruleRelationships = ArrayBuffer[RuleRelationship]()
    for (ruleRel <- ruleRels) {
      val ruleRelArr =  ruleRel.split(" ")
      val firstRuleId = ruleRelArr(0)
      val operation = ruleRelArr(1)
      val secondRuleId = ruleRelArr(2)
      val ruleRelationship = RuleRelationship(firstRuleId, secondRuleId, operation)
      ruleRelationships += ruleRelationship
    }
    ruleRelationships.toArray
  }
}
