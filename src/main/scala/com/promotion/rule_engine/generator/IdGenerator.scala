package com.promotion.rule_engine.generator

import scala.util.Random

object IdGenerator {

  def generate(prefix: String, totLen: Int): String = {
    val timestampStr = (System.currentTimeMillis / 60000).toString
    val randomPartLen = totLen - prefix.length - timestampStr.length
    val randomStr = Random.alphanumeric.take(randomPartLen).mkString.toUpperCase
    prefix + timestampStr + randomStr
  }
}