package com.promotion.rule_engine.bootstrap

import com.mongodb.casbah.{MongoClient => MongoDBClient, MongoClientURI}

object MongoClient {

  lazy val db = init

  def init = {
    val uri = ConfigManager.getValue("mongo.uri")
    val dbName = ConfigManager.getValue("mongo.dbName")
    MongoDBClient(MongoClientURI(uri))(dbName)
  }

  def getConnection = db
}
