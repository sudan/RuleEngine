package com.promotion.rule_engine

/**
 * Created by sudan on 8/4/16.
 */
object Constants {

  val RULE_COLLECTION = "rules"
  val CAMPAIGN_COLLECTION = "campaigns"
  val SALE_COLLECTION = "sales"

  val SOFT_DELETED = "soft_deleted"
  val ID = "id"
  val SEPARATOR = ":"

  val SET_OP = "$set"

  val RULE_ID_LENGTH = 16
  val RULE_ID_PREFIX = "RUL"

  val CAMPAIGN_ID_LENGTH = 16
  val CAMPAIGN_ID_PREFIX = "CAM"

  val SALE_ID_LENGTH = 16
  val SALE_ID_PREFIX = "SAL"

  val COUNTRIES = "countries"
  val STATES = "states"
  val CITIES = "cities"
  val AREAS = "areas"
  val PINCODES = "pincodes"

  val MAIN_CATEGORIES = "main_categories"
  val SUB_CATEGORIES = "sub_categories"
  val VERTICALS = "verticals"
  val PRODUCT_IDS = "product_ids"

  val REGION = "region"
  val COUNTRY = "country"
  val STATE = "state"
  val CITY = "city"
  val AREA = "area"
  val PINCODE = "pincode"

  val MAIN_CATEGORY = "main_category"
  val SUB_CATEGORY = "sub_category"
  val VERTICAL = "vertical"
  val PRODUCT_ID = "product_id"
  val CATEGORY = "category"

  val RULE = "rule"
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

  val CAMPAIGN_ID = "_id"
  val CAMPAIGN_RULE_IDS = "rule_ids"
  val CAMPAIGN_START_DATE = "start_date"
  val CAMPAIGN_END_DATE = "end_date"
  val CAMPAIGN_RULE_RELATIONSHIPS = "relationships"
  val CAMPAIGN_FIRST_RULE_ID = "rule_one"
  val CAMPAIGN_SECOND_RULE_ID = "rule_two"
  val RULE_OPERATION = "operation"

  val SALE_ID = "_id"
  val SALE_CAMPAIGN_IDS = "campaign_ids"

  val DISPLAY_FORMAT = "YYYY-MM-dd"
  val SENTINEL_ID = "-1"
  val SENTINEL_BOOST = -1
  val SENTINEL_TIMESTAMP = -1

  val RULE_RELATIONSHIP = "relationships"

  val OP_AND = "and"
}
