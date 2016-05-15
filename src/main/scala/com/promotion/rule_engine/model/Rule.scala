package com.promotion.rule_engine.model

import scala.collection.mutable.Map

case class Rule(
                 id: String,
                 name: String,
                 description: String,
                 createdOn: Long,
                 modifiedAt: Long,
                 version: String,
                 owner: String,
                 discount: Double,
                 boost: Integer,
                 properties: Map[String, Array[String]],
                 regionList: RegionList,
                 categoryList: CategoryList,
                 isActive: Boolean
                 ) {


  def isGlobal = {
    (isRegionEmpty && isPropertiesEmpty) || (isCategoryEmpty && isPropertiesEmpty) ||
      (isRegionEmpty && isCategoryEmpty) || (!categoryList.productIds.isEmpty)
  }
  def isRegionEmpty = {
    regionList.countries.isEmpty && regionList.states.isEmpty && regionList.cities.isEmpty &&
      regionList.areas.isEmpty && regionList.pincodes.isEmpty
  }

  def isCategoryEmpty = {
    categoryList.mainCategories.isEmpty && categoryList.subCategories.isEmpty &&
      categoryList.verticals.isEmpty && categoryList.productIds.isEmpty
  }

  def isPropertiesEmpty = {
    properties.isEmpty
  }
}

