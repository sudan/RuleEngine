package com.promotion.rule_engine

import com.promotion.rule_engine.bootstrap.ConfigManager
import com.promotion.rule_engine.bootstrap.MongoClient

object RuleEngine extends App {

	ConfigManager.init

	val mongoURL = ConfigManager.getValue("mongo.uri")
	val mongoDBName = ConfigManager.getValue("mongo.dbName")
	val db = MongoClient.init(mongoURL, mongoDBName)
}
