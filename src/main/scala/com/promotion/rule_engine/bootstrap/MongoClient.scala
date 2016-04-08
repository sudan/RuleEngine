package com.promotion.rule_engine.bootstrap

import com.mongodb.casbah.{MongoClient => MongoDBClient, MongoClientURI}
import com.mongodb.casbah.Imports._

object MongoClient {

	def init(uri: String, dbName: String) = {
		val db = MongoDBClient(MongoClientURI(uri))(dbName)
		db
	}
}
