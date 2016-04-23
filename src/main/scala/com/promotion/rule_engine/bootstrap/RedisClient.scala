package com.promotion.rule_engine.bootstrap

import com.redis.{RedisClient => RedisDBClient}

object RedisClient {

  lazy val db = init

  def init = {
    val hostName = ConfigManager.getValue("redis.hostName")
    val port = (ConfigManager.getValue("redis.portNumber")).toInt
    new RedisDBClient(hostName, port)
  }

  def getConnection = db
}
