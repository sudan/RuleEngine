package com.promotion.rule_engine.builder

import com.mongodb.casbah.Imports._
import com.promotion.rule_engine.Constants
import com.promotion.rule_engine.model.CategoryList

/**
 * Created by sudan on 8/4/16.
 */
object CategoryListBuilder {

  def build(categoryList: CategoryList): DBObject = {
    val categoryListBuilder = MongoDBObject.newBuilder
    categoryListBuilder += Constants.MAIN_CATEGORIES -> categoryList.mainCategories
    categoryListBuilder += Constants.SUB_CATEGORIES -> categoryList.subCategories
    categoryListBuilder += Constants.VERTICALS -> categoryList.verticals
    categoryListBuilder += Constants.PRODUCT_IDS -> categoryList.productIds
    categoryListBuilder.result
  }
}
