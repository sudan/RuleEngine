package com.promotion.rule_engine.model

case class CategoryList(
		mainCategories: List[String],
		subCategories: List[String],
		verticals: List[String],
		productIds: List[String]
	)
