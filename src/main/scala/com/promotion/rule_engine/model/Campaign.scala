package com.promotion.rule_engine.model

case class Campaign(
                     id: String,
                     ruleIds: Array[String],
                     startDate: Long,
                     endDate: Long,
                     ruleRelationships: Array[RuleRelationship]
                     )
