package com.promotion.rule_engine.converter

import com.promotion.rule_engine.Constants
import com.promotion.rule_engine.model.CategoryList
import play.api.libs.json.Json.JsValueWrapper

import scala.collection.mutable
import scala.collection.mutable.Map
import play.api.libs.json.{JsObject, Json, Writes, JsValue}

/**
 * Created by sudan on 10/04/16.
 */
object RulePropertyConverter {

  def toJson(property: Map[String, Array[String]]): JsValue = {

    implicit val propertyWriter = new Writes[Map[String, Array[String]]] {
      def writes(property: Map[String, Array[String]]) = Json.obj(
        property.map {case(key,value) => (key, Json.toJsFieldJsValueWrapper(value))}.toSeq:_*
      )
    }
    Json.toJson(property)
  }
}

