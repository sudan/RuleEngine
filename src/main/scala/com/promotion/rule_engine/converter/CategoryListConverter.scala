package com.promotion.rule_engine.converter

import com.promotion.rule_engine.Constants
import com.promotion.rule_engine.model.CategoryList
import play.api.libs.functional.syntax._
import play.api.libs.json._


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

  def fromJson(json: JsValue): CategoryList = {

    implicit val categoryListReader: Reads[CategoryList] = (
      (JsPath \ Constants.MAIN_CATEGORIES).read[Array[String]] and
      (JsPath \ Constants.SUB_CATEGORIES).read[Array[String]] and
      (JsPath \ Constants.VERTICALS).read[Array[String]] and
      (JsPath \ Constants.PRODUCT_IDS).read[Array[String]]
    ) (CategoryList.apply _)
    Json.fromJson(json).get
  }
}
