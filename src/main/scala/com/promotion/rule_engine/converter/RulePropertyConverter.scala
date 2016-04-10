package com.promotion.rule_engine.converter

import play.api.libs.json.{JsValue, Json, Writes}

import scala.collection.mutable.Map

/**
 * Created by sudan on 10/04/16.
 */
object RulePropertyConverter {

  def toJson(property: Map[String, Array[String]]): JsValue = {

    implicit val propertyWriter = new Writes[Map[String, Array[String]]] {
      def writes(property: Map[String, Array[String]]) = Json.obj(
        property.map { case (key, value) => (key, Json.toJsFieldJsValueWrapper(value)) }.toSeq: _*
      )
    }
    Json.toJson(property)
  }
}

