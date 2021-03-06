package com.promotion.rule_engine.dao.impl

import com.mongodb.casbah.commons.MongoDBObject
import com.promotion.rule_engine.Constants
import com.promotion.rule_engine.bootstrap.{MongoClient, RedisClient}
import com.promotion.rule_engine.builder.RuleBuilder
import com.promotion.rule_engine.dao.api.RuleDao
import com.promotion.rule_engine.generator.IdGenerator
import com.promotion.rule_engine.mapper.RuleMapper
import com.promotion.rule_engine.model.Rule

/**
 * Created by sudan on 8/4/16.
 */
class RuleDaoImpl extends RuleDao {

  val mongoClient = MongoClient.getConnection
  val redisClient = RedisClient.getConnection

  def insert(rule: Rule): String = {

    val id = IdGenerator.generate(Constants.RULE_ID_PREFIX, Constants.RULE_ID_LENGTH)
    val ruleObj = RuleBuilder.build(rule, id)
    val collection = mongoClient(Constants.RULE_COLLECTION)
    collection.insert(ruleObj)
    id
  }

  def get(ruleId: String): Either[Throwable, Rule] = {

    val collection = mongoClient(Constants.RULE_COLLECTION)
    val document = collection.findOne(MongoDBObject(Constants.RULE_ID -> ruleId,
      Constants.SOFT_DELETED -> false))
    document match {
      case Some(_) =>
        Right(RuleMapper.map(document.get))
      case None => Left(throw new Exception("Invalid ruleId " + ruleId))
    }
  }

  def update(rule: Rule): Either[Throwable, Rule] = {
    val ruleObj = RuleBuilder.build(rule, rule.id)
    val collection = mongoClient(Constants.RULE_COLLECTION)
    val query = MongoDBObject(Constants.RULE_ID -> rule.id)
    collection.update(query, ruleObj)
    Right(rule)
  }

  def delete(ruleId: String): Unit = {

    var query = MongoDBObject(Constants.RULE_ID -> ruleId)
    val collection = mongoClient(Constants.RULE_COLLECTION)
    collection.update(query, MongoDBObject(Constants.SET_OP ->
      MongoDBObject(Constants.SOFT_DELETED -> true)))
  }

  def activate(rules: Array[Rule]): Unit = {
    for (rule <- rules) {
      val query = MongoDBObject(Constants.RULE_ID -> rule.id)
      val collection = mongoClient(Constants.RULE_COLLECTION)
      collection.update(query, MongoDBObject(Constants.SET_OP ->
        MongoDBObject(Constants.RULE_IS_ACTIVE -> true)))
    }
  }

  def getDiscountedAttrs(ruleId: String): Map[String, String] = {

    val discount = redisClient.hget(Constants.RULE + Constants.SEPARATOR + ruleId,
      Constants.DISCOUNT).get
    val boost = redisClient.hget(Constants.RULE + Constants.SEPARATOR + ruleId,
      Constants.RULE_BOOST).get
    Map(Constants.DISCOUNT -> discount, Constants.RULE_BOOST -> boost)
  }

  def getRuleRelationships(): Map[String, String] = {
    redisClient.hgetall(Constants.RULE_RELATIONSHIP).get
  }

}
