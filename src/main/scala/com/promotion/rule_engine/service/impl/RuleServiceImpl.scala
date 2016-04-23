package com.promotion.rule_engine.service.impl

import com.promotion.rule_engine.converter.RuleConverter
import com.promotion.rule_engine.dao.impl.RuleDaoImpl
import com.promotion.rule_engine.service.api.RuleService
import play.api.libs.json.JsValue

/**
 * Created by sudan on 10/04/16.
 */
class RuleServiceImpl extends RuleService {

  val ruleDao = new RuleDaoImpl

  def createRule(json: JsValue): String = {
    val rule = RuleConverter.fromJson(json)
    ruleDao.insert(rule)
  }
}
