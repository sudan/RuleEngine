package com.promotion.rule_engine.builder

import com.mongodb.casbah.Imports._
import com.promotion.rule_engine.Constants
import com.promotion.rule_engine.model.Rule

/**
 * Created by sudan on 8/4/16.
 */
object RuleBuilder {

  def build(rule: Rule, ruleId: String): DBObject = {
    val ruleBuilder = MongoDBObject.newBuilder
    val categoryList = CategoryListBuilder.build(rule.categoryList)
    val regionList = RegionListBuilder.build(rule.regionList)
    ruleBuilder += Constants.RULE_ID -> ruleId
    ruleBuilder += Constants.RULE_NAME -> rule.name
    ruleBuilder += Constants.RULE_DESCRIPTION -> rule.description
    ruleBuilder += Constants.RULE_CREATED_ON -> rule.createdOn
    ruleBuilder += Constants.RULE_MODIFIED_AT -> rule.modifiedAt
    ruleBuilder += Constants.RULE_OWNER -> rule.owner
    ruleBuilder += Constants.RULE_VERSION -> rule.version
    ruleBuilder += Constants.RULE_BOOST -> rule.boost
    ruleBuilder += Constants.RULE_PROPERTIES -> rule.properties
    ruleBuilder += Constants.RULE_IS_ACTIVE -> rule.isActive
    ruleBuilder += Constants.CATEGORY_LIST -> categoryList
    ruleBuilder += Constants.REGION_LIST -> regionList
    ruleBuilder += Constants.SOFT_DELETED -> false
    ruleBuilder.result
  }

}
