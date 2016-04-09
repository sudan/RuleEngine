package com.promotion.rule_engine.model

case class CategoryList(
                         mainCategories: Array[String],
                         subCategories: Array[String],
                         verticals: Array[String],
                         productIds: Array[String]
                         )
