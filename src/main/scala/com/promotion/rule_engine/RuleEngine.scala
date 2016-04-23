package com.promotion.rule_engine

import com.promotion.rule_engine.bootstrap.{ConfigManager, MongoClient, RedisClient}

object RuleEngine extends App {
  ConfigManager.init
  MongoClient.init
  RedisClient.init
}
