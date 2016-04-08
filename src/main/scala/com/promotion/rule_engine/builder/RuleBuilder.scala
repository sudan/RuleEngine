package com.promotion.rule_engine.builder

import com.mongodb.casbah.Imports._
import com.promotion.rule_engine.generator.IdGenerator
import com.promotion.rule_engine.model.Rule
import com.promotion.rule_engine.Constants

/**
  * Created by sudan on 8/4/16.
  */
object RuleBuilder {

  def build(rule: Rule, regionList: DBObject, categoryList: DBObject, ruleId: String): DBObject = {
    val ruleBuilder = MongoDBObject.newBuilder
    ruleBuilder += Constants.RULE_ID -> ruleId
    ruleBuilder += Constants.RULE_NAME -> rule.name
    ruleBuilder += Constants.RULE_DESCRIPTION -> rule.description
    ruleBuilder += Constants.RULE_CREATED_ON -> System.currentTimeMillis
    ruleBuilder += Constants.RULE_MODIFIED_AT -> System.currentTimeMillis
    ruleBuilder += Constants.RULE_OWNER -> rule.owner
    ruleBuilder += Constants.RULE_VERSION -> rule.version
    ruleBuilder += Constants.RULE_BOOST -> rule.boost
    ruleBuilder += Constants.RULE_PROPERTIES -> rule.properties
    ruleBuilder += Constants.RULE_IS_ACTIVE -> rule.isActive
    ruleBuilder += Constants.CATEGORY_LIST -> categoryList
    ruleBuilder += Constants.REGION_LIST -> regionList
    ruleBuilder.result
  }

}
