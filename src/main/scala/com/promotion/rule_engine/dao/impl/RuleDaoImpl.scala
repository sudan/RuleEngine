package com.promotion.rule_engine.dao.impl

import com.mongodb.casbah.MongoDB
import com.mongodb.casbah.commons.MongoDBObject
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

  def get(ruleId: String): Either[Throwable, Rule] = {

    val collection = db(Constants.RULE_COLLECTION)
    val document = collection.findOneByID(ruleId)
    document match {
      case Some(_) =>
        Right(RuleMapper.map(document.get))
      case None => Left(throw new Exception("Invalid ruleId " + ruleId))
    }
  }

  def update(rule: Rule): Either[Throwable, Rule] = {
    val categoryList = CategoryListBuilder.build(rule.categoryList)
    val regionList = RegionListBuilder.build(rule.regionList)
    val ruleObj = RuleBuilder.build(rule, regionList, categoryList, rule.id)
    val collection = db(Constants.RULE_COLLECTION)
    val query = MongoDBObject(Constants.RULE_ID -> rule.id)
    collection.update(query, ruleObj)
    Right(rule)
  }
}
