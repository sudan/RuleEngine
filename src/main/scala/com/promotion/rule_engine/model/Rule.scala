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
                 )

