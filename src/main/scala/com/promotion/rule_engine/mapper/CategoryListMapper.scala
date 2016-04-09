package com.promotion.rule_engine.mapper

import com.mongodb.{BasicDBList, DBObject}
import com.promotion.rule_engine.Constants
import com.promotion.rule_engine.model.CategoryList

/**
 * Created by sudan on 09/04/16.
 */
object CategoryListMapper {

  def map(dBObject: DBObject): CategoryList = {

    val categoryList = dBObject.get(Constants.CATEGORY_LIST).asInstanceOf[DBObject]
    val mainCategories = categoryList.get(Constants.MAIN_CATEGORIES).asInstanceOf[BasicDBList].toArray.map(_.toString)
    val subCategories = categoryList.get(Constants.SUB_CATEGORIES).asInstanceOf[BasicDBList].toArray.map(_.toString)
    val verticals = categoryList.get(Constants.VERTICALS).asInstanceOf[BasicDBList].toArray.map(_.toString)
    val productIds = categoryList.get(Constants.PRODUCT_IDS).asInstanceOf[BasicDBList].toArray.map(_.toString)

    CategoryList(mainCategories, subCategories, verticals, productIds)
  }
}
