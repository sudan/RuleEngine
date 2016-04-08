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
}
