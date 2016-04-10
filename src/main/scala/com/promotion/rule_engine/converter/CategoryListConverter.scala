package com.promotion.rule_engine.converter

import com.promotion.rule_engine.model.CategoryList
import play.api.libs.json.{Json, Writes, JsValue}
import com.promotion.rule_engine.Constants

/**
 * Created by sudan on 10/04/16.
 */
object CategoryListConverter {

  def toJson(categoryList: CategoryList): JsValue = {

    implicit val categoryListWriter = new Writes[CategoryList] {
      def writes(categoryList: CategoryList) = Json.obj(
        Constants.MAIN_CATEGORIES -> categoryList.mainCategories,
        Constants.SUB_CATEGORIES -> categoryList.subCategories,
        Constants.VERTICALS -> categoryList.verticals,
        Constants.PRODUCT_IDS -> categoryList.productIds
      )
    }
    Json.toJson(categoryList)
  }
}
