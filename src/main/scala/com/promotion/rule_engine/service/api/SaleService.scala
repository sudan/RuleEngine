package com.promotion.rule_engine.service.api

import play.api.libs.json.JsValue

/**
 * Created by sudan on 23/04/16.
 */
trait SaleService {
  /**
   * Create a new sale and return the sale ID
   * @param json
   * @return
   */
  def createSale(json: JsValue): String

  /**
   * Get the sale given the sale ID
   * @param saleId
   * @return
   */
  def getSale(saleId: String): Either[Throwable, JsValue]

  /**
   * Update the sale and return the updated sale
   * @param json
   * @return
   */
  def updateSale(json: JsValue): Either[Throwable, JsValue]

  /**
   * Delete the sale given the saleID
   * @param saleId
   */
  def deleteSale(saleId: String): Unit
}
