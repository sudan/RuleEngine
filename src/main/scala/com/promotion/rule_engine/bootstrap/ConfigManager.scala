package com.promotion.rule_engine.bootstrap

import java.util.Properties
import java.io.FileInputStream

object ConfigManager {

	val properties = new Properties()

	def init = {
		try {
			properties.load(new FileInputStream("src/main/resources/config.properties"))
		} catch {
			case e: Exception =>
				e.printStackTrace()
				sys.exit(1)
		}
	}

	def getValue(key: String) = {
		properties.getProperty(key)
	}
} 