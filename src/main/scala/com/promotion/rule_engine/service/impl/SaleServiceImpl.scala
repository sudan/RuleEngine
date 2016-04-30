package com.promotion.rule_engine.service.impl

import com.promotion.rule_engine.converter.SaleConverter
import com.promotion.rule_engine.dao.impl.{RuleDaoImpl, CampaignDaoImpl, SaleDaoImpl}
import com.promotion.rule_engine.model.{RuleRelationship, Campaign, Rule}
import com.promotion.rule_engine.service.api.SaleService
import play.api.libs.json.JsValue

import scala.collection.mutable.ArrayBuffer

/**
 * Created by sudan on 23/04/16.
 */
class SaleServiceImpl extends SaleService {

  val saleDao = new SaleDaoImpl
  val campaignDao = new CampaignDaoImpl
  val ruleDao = new RuleDaoImpl

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

  def startSale(saleId: String): Either[Throwable, Boolean] = {
    val sale = saleDao.get(saleId)
    sale match {
      case Left(exception) => throw exception
      case Right(sale) =>
        val campaignIds = sale.campaignIds
        val rules = ArrayBuffer[Rule]()
        for (campaignId <- campaignIds) {
          val campaign = campaignDao.get(campaignId)
          campaign match {
            case Left(exception) => println("Campaign " + campaignId + " " + exception)
            case Right(campaign) =>
              val ruleIds = campaign.ruleIds
              for (ruleId <- ruleIds) {
                val rule = ruleDao.get(ruleId)
                rule match {
                  case Left(exception) => println("Rule " + ruleId + " " + exception)
                  case Right(rule) =>
                    rules += rule
                }
              }
          }
        }
        saleDao.applyRules(rules.toArray)
        Right(true)
    }
  }
}
