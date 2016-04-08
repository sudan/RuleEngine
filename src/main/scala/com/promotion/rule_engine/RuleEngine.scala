package com.promotion.rule_engine

import com.promotion.rule_engine.bootstrap.ConfigManager
import com.promotion.rule_engine.bootstrap.MongoClient
import com.promotion.rule_engine.bootstrap.RedisClient

object RuleEngine extends App {

	ConfigManager.init

	val mongoURL = ConfigManager.getValue("mongo.uri")
	val mongoDBName = ConfigManager.getValue("mongo.dbName")
	val mongoConn = MongoClient.init(mongoURL, mongoDBName)

	val redisHostName = ConfigManager.getValue("redis.hostName")
	val redisPort = (ConfigManager.getValue("redis.portNumber")).toInt
	val redisConn = RedisClient.init(redisHostName, redisPort)

}
