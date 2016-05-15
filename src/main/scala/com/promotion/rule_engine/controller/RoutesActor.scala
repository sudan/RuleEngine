package com.promotion.rule_engine.controller

import akka.actor.{Actor, ActorRefFactory}

/**
 * Created by sudan on 14/05/16.
 */
class RoutesActor extends Actor with Routes {

  override val actorRefFactory: ActorRefFactory = context

  def receive = runRoute(routes)
}

trait Routes extends RuleController with CampaignController with SaleController with DiscountController {
  val routes = {
    createRuleRoute ~
      getRuleRoute ~
      updateRuleRoute ~
      deleteRuleRoute ~
      createCampaignRoute ~
      getCampaignRoute ~
      updateCampaignRoute ~
      deleteCampaignRoute ~
      createSaleRoute ~
      getSaleRoute ~
      updateSaleRoute ~
      deleteSaleRoute ~
      startSaleRoute ~
      getDiscountRoute
  }
}
