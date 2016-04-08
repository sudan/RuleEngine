package com.promotion.rule_engine.model

import java.util.Date

case class Campaign(id: String,
					rules: List[Rule],
					startDate: Date,
					endDate: Date)
