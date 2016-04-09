package com.promotion.rule_engine.dao.impl

import com.mongodb.casbah.MongoDB
import com.promotion.rule_engine.Constants
import com.promotion.rule_engine.builder.{CategoryListBuilder, RegionListBuilder, RuleBuilder}
import com.promotion.rule_engine.dao.api.RuleDao
import com.promotion.rule_engine.generator.IdGenerator
import com.promotion.rule_engine.mapper.RuleMapper
import com.promotion.rule_engine.model.Rule

/**
 * Created by sudan on 8/4/16.
 */
class RuleDaoImpl(db: MongoDB) extends RuleDao {

  def insert(rule: Rule): String = {

    val id = IdGenerator.generate(Constants.RULE_ID_PREFIX, Constants.RULE_ID_LENGTH)
    val categoryList = CategoryListBuilder.build(rule.categoryList)
    val regionList = RegionListBuilder.build(rule.regionList)
    val ruleObj = RuleBuilder.build(rule, regionList, categoryList, id)
    val collection = db(Constants.RULE_COLLECTION)
    collection.insert(ruleObj)
    return id
  }

  /**
   * Get the rule object corresponding to ruleId
   * @param ruleId
   * @return
   */
  def get(ruleId: String): Either[Rule, Throwable] = {

    val collection = db(Constants.RULE_COLLECTION)
    val document = collection.findOneByID(ruleId)
    document match {
      case Some(_) =>
        Left(RuleMapper.map(document.get))
      case None => Right(throw new Exception("Invalid ruleId " + ruleId))
    }
  }
}
