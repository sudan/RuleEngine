package com.promotion.rule_engine.model

import java.util.Date

case class Rule(
		id: String,
		name: String,
		description: String,
		createdOn: Date,
		modifiedAt: Date,
		version: String,
		owner: String,
		discount: Double,
		boost: Integer,
		properties: Map[String, List[String]],
		regionList: RegionList,
		categoryList: CategoryList,
		isActive: Boolean
	)

