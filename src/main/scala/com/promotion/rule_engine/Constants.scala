package com.promotion.rule_engine

/**
 * Created by sudan on 8/4/16.
 */
object Constants {

  val SOFT_DELETED = "soft_deleted"

  val SET_OP = "$set"

  val RULE_ID_LENGTH = 16
  val RULE_ID_PREFIX = "RUL"
  val RULE_COLLECTION = "rules"

  val COUNTRIES = "countries"
  val STATES = "states"
  val CITIES = "cities"
  val AREAS = "areas"
  val PINCODES = "pincodes"

  val MAIN_CATEGORIES = "main_categories"
  val SUB_CATEGORIES = "sub_categories"
  val VERTICALS = "verticals"
  val PRODUCT_IDS = "product_ids"

  val RULE_ID = "_id"
  val RULE_NAME = "name"
  val RULE_DESCRIPTION = "description"
  val RULE_CREATED_ON = "created_on"
  val RULE_MODIFIED_AT = "modified_at"
  val RULE_OWNER = "owner"
  val RULE_VERSION = "version"
  val RULE_BOOST = "boost"
  val RULE_PROPERTIES = "properties"
  val RULE_IS_ACTIVE = "is_active"
  val DISCOUNT = "discount"
  val REGION_LIST = "region_list"
  val CATEGORY_LIST = "category_list"
}
