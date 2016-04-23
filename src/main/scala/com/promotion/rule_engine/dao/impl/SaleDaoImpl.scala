package com.promotion.rule_engine.dao.impl

import com.mongodb.casbah.commons.MongoDBObject
import com.promotion.rule_engine.Constants
import com.promotion.rule_engine.bootstrap.MongoClient
import com.promotion.rule_engine.builder.SaleBuilder
import com.promotion.rule_engine.dao.api.SaleDao
import com.promotion.rule_engine.generator.IdGenerator
import com.promotion.rule_engine.mapper.SaleMapper
import com.promotion.rule_engine.model.Sale

/**
 * Created by sudan on 09/04/16.
 */
class SaleDaoImpl extends SaleDao {

  val db = MongoClient.getConnection

  def insert(sale: Sale): String = {
    val id = IdGenerator.generate(Constants.SALE_ID_PREFIX, Constants.SALE_ID_LENGTH)
    val saleObj = SaleBuilder.build(sale, id)
    val collection = db(Constants.SALE_COLLECTION)
    collection.insert(saleObj)
    id
  }

  def get(saleId: String): Either[Throwable, Sale] = {
    val collection = db(Constants.SALE_COLLECTION)
    val document = collection.findOne(MongoDBObject(Constants.SALE_ID -> saleId,
      Constants.SOFT_DELETED -> false))
    document match {
      case Some(_) =>
        Right(SaleMapper.map(document.get))
      case None => Left(throw new Exception("Invalid saleID " + saleId))
    }
  }

  def update(sale: Sale): Either[Throwable, Sale] = {
    val saleObj = SaleBuilder.build(sale, sale.id)
    val collection = db(Constants.SALE_COLLECTION)
    val query = MongoDBObject(Constants.SALE_ID -> sale.id)
    collection.update(query, saleObj)
    Right(sale)
  }

  def delete(saleId: String): Unit = {
    val query = MongoDBObject(Constants.SALE_ID -> saleId)
    val collection = db(Constants.SALE_COLLECTION)
    collection.update(query, MongoDBObject(Constants.SET_OP ->
      MongoDBObject(Constants.SOFT_DELETED -> true)))
  }
}
