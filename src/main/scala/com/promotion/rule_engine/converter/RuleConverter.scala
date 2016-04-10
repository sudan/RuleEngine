package com.promotion.rule_engine.converter


import com.promotion.rule_engine.model.Rule
import org.joda.time.DateTime
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter, ISODateTimeFormat}
import play.api.libs.json.{Json, Writes, JsValue}
import com.promotion.rule_engine.Constants

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
}
