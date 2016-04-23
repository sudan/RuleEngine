package com.promotion.rule_engine.service.impl

import com.promotion.rule_engine.converter.SaleConverter
import com.promotion.rule_engine.dao.impl.SaleDaoImpl
import com.promotion.rule_engine.service.api.SaleService
import play.api.libs.json.JsValue

/**
 * Created by sudan on 23/04/16.
 */
class SaleServiceImpl extends SaleService {

  val saleDao = new SaleDaoImpl

  def createSale(json: JsValue): String = {
    val sale = SaleConverter.fromJson(json)
    saleDao.insert(sale)
  }

  def getSale(saleId: String): Either[Throwable, JsValue] = {
    val sale = saleDao.get(saleId)
    sale match {
      case Left(exception) => throw exception
      case Right(sale) => Right(SaleConverter.toJson(sale))
    }
  }

  def updateSale(json: JsValue): Either[Throwable, JsValue] = {
    val sale = SaleConverter.fromJson(json)
    saleDao.update(sale) match {
      case Left(exception) => Left(throw exception)
      case Right(sale) => Right(SaleConverter.toJson(sale))
    }
  }


  def deleteSale(saleId: String): Unit = {
    saleDao.delete(saleId)
  }
}
