package com.promotion.rule_engine.mapper

import com.mongodb.{BasicDBObject, DBObject, BasicDBList}
import com.promotion.rule_engine.Constants
import com.promotion.rule_engine.model.Rule

import scala.collection.JavaConverters._
import scala.collection.mutable.Map

/**
 * Created by sudan on 09/04/16.
 */
object RuleMapper {

  def map(dBObject: DBObject): Rule = {
    val id = dBObject.get(Constants.RULE_ID).asInstanceOf[String]
    val name = dBObject.get(Constants.RULE_NAME).asInstanceOf[String]
    val description = dBObject.get(Constants.RULE_DESCRIPTION).asInstanceOf[String]
    val createdOn = dBObject.get(Constants.RULE_CREATED_ON).asInstanceOf[Long]
    val modifiedAt = dBObject.get(Constants.RULE_MODIFIED_AT).asInstanceOf[Long]
    val owner = dBObject.get(Constants.RULE_OWNER).asInstanceOf[String]
    val version = dBObject.get(Constants.RULE_VERSION).asInstanceOf[String]
    val discount = dBObject.get(Constants.DISCOUNT).asInstanceOf[Double]
    val boost = dBObject.get(Constants.RULE_BOOST).asInstanceOf[Int]
    val propertiesMap = dBObject.get(Constants.RULE_PROPERTIES).asInstanceOf[BasicDBObject].toMap.asScala
    val properties = propertiesMap.map {case(k,v) => (k.toString,v.asInstanceOf[BasicDBList].toArray.map(_.toString)) }
    val regionList = RegionListMapper.map(dBObject)
    val categoryList = CategoryListMapper.map(dBObject)
    val isActive = dBObject.get(Constants.RULE_IS_ACTIVE).asInstanceOf[Boolean]
    Rule(id, name, description, createdOn, modifiedAt, owner, version, discount, boost, properties,
      regionList, categoryList, isActive)
  }

}
