package com.promotion.rule_engine.converter


import com.promotion.rule_engine.Constants
import com.promotion.rule_engine.model.Rule
import org.joda.time.format.DateTimeFormat
import play.api.libs.json._

/**
 * Created by sudan on 10/04/16.
 */
object RuleConverter {

  def toJson(rule: Rule): JsValue = {

    val format = DateTimeFormat.forPattern(Constants.DISPLAY_FORMAT)
    implicit val ruleWriter = new Writes[Rule] {
      def writes(rule: Rule) = Json.obj(
        Constants.RULE_ID -> rule.id,
        Constants.RULE_NAME -> rule.name,
        Constants.RULE_DESCRIPTION -> rule.description,
        Constants.RULE_CREATED_ON -> format.print(rule.createdOn),
        Constants.RULE_MODIFIED_AT -> format.print(rule.modifiedAt),
        Constants.RULE_OWNER -> rule.owner,
        Constants.RULE_BOOST -> rule.owner,
        Constants.RULE_VERSION -> rule.version,
        Constants.DISCOUNT -> rule.discount,
        Constants.RULE_IS_ACTIVE -> rule.isActive,
        Constants.RULE_PROPERTIES -> RulePropertyConverter.toJson(rule.properties),
        Constants.REGION_LIST -> RegionListConverter.toJson(rule.regionList),
        Constants.CATEGORY_LIST -> CategoryListConverter.toJson(rule.categoryList)
      )
    }
    Json.toJson(rule)
  }

  def fromJson(json: JsValue): Rule = {

    val idResult = (json \ Constants.ID).validate[String]
    val id = idResult match {
      case s: JsSuccess[String] => idResult.asInstanceOf[String]
      case e: JsError => Constants.SENTINEL_ID
    }
    val name = (json \ Constants.RULE_NAME).as[String]
    val description = (json \ Constants.RULE_DESCRIPTION).as[String]
    val createdOn = System.currentTimeMillis
    val modifiedAt = System.currentTimeMillis
    val owner = (json \ Constants.RULE_OWNER).as[String]
    val boost = (json \ Constants.RULE_BOOST).as[Int]
    val version = (json \ Constants.RULE_VERSION).as[String]
    val discount = (json \ Constants.DISCOUNT).as[Double]
    val isActive = (json \ Constants.RULE_IS_ACTIVE).as[Boolean]
    val propertyMap = (json \ Constants.RULE_PROPERTIES).as[Map[String, Array[String]]]
    val properties = collection.mutable.Map[String, Array[String]]() ++= propertyMap
    val regionList = RegionListConverter.fromJson(json \ Constants.REGION_LIST)
    val categoryList = CategoryListConverter.fromJson(json \ Constants.CATEGORY_LIST)
    Rule(id, name, description, createdOn, modifiedAt, version, owner, discount, boost,
      properties, regionList, categoryList, isActive)
  }

}
