package com.promotion.rule_engine

import com.promotion.rule_engine.bootstrap.ConfigManager

object RuleEngine extends App {

	ConfigManager.init

	val mongoURI = ConfigManager.getValue("mongo.uri")
}