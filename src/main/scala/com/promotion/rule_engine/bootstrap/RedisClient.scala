package com.promotion.rule_engine.bootstrap

import com.redis.{RedisClient => RedisDBClient}

object RedisClient {

  def init(hostName: String, port: Int) = {
    val db = new RedisDBClient(hostName, port)
    db
  }
}
