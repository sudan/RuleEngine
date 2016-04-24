package com.promotion.rule_engine.converter

import com.promotion.rule_engine.Constants
import play.api.libs.json.JsValue

/**
 * Created by sudan on 24/04/16.
 */
object ProductAttributeConverter {

  def fromJson(json: JsValue): Map[String, String] = {
    (json \ Constants.RULE_PROPERTIES).as[Map[String, String]]
  }
}

