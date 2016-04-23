package com.promotion.rule_engine.dao.api

import com.promotion.rule_engine.model.Sale

/**
 * Created by sudan on 10/04/16.
 */
trait SaleDao {

  /**
   * Create a sale and return the saleId
   * @param sale
   * @return
   */
  def insert(sale: Sale): String

  /**
   * Get the sale given a saleId
   * @param saleId
   * @return
   */
  def get(saleId: String): Either[Throwable, Sale]

  /**
   * Update the sale and return the sale object updated
   * @param sale
   * @return
   */
  def update(sale: Sale): Either[Throwable, Sale]

  /**
   * Soft delete the sale given the saleId
   * @param saleId
   */
  def delete(saleId: String): Unit
}
