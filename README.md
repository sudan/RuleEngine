# RuleEngine

A generalized promotion rule engine for products which allows to define rules for discounting without requiring a code change

# Technologies used

1. Scala
2. Spray (For REST APIs)
3. MongoDB(Casbah)
4. Redis

# Overview

Basic idea behind building this rule engine is to create a minimal module to define rules for ecommerce products over dimensions such as region, category and product specific properties which is used as a criteria to calculate discounts.

Hierarchy

Sample Rule Schema

```bash
{
  "id": "<rule id>", // Generated for creation and can be used for updation,
  "name": "<rule name>",
  "description": "<rule description>",
  "version": "<rule version>",
  "owner": "<rule owner>", // a string field which denotes the owner who created this
  "discount": "<rule discount>", // a double value which denotes discount
  "boost": "<rule boost>", // an integer which is used to decide preference when two rules collide,
  "properties": {
    "property1": ["value11", "value12" ...],
    "property2": ["value21", "value22" ...]
  },
  "region_list": {
    "countries": [<list of countries>],
    "states": [<list of states>],
    "cities": [<list of cities>],
    "areas": [<list of areas>],
    "pincodes": [<list of pincodes>]
  },
  "category_list": {
    "main_categories": [<list of main categories>],
    "sub_categories": [<list of sub categories>],
    "verticals": [<list of verticals>],
    "product_ids": [<list of product ids>]
  }
}
```

Collection of rules form a campaign

Campaign Schema

```bash
{
  "id": "<campaign id>", // generated on creation and can be used for updation
  "rule_ids": [<list of rule ids>],
  "start_date": "<campaign start date>",
  "end_date": "<campaign end date>"
}
```

Collection of campaigns form a sale

```bash
{
  "id": "<sale id>", // generated on creation and can be used for updation
  "campaign_ids": [<list of campaign ids]
}
```

By default rules will be in dormant state unless the sale is explictly started.

Rules can be defined on

1. Region - Which includes countries, states, cities, areas and pincodes. Order of priority is bottom up where pincodes are given the most priority and countries given the least to narrow down
2. Category - Which includes main categories, sub categories, verticals and product ids. Order of priority is bottom up where product ids are given the most priority and main categories given the least to narrow down
3. List of properties where each is a key to list of values

Given a product with information on buyer's region and product properties (only relevant ones) and category information, it fetches all rules which match region (based on priority mentioned above), which match category (based on priority mentioned above) and properties and apply the following conditions to fetch ruleIds

```bash
Region = r (rule ids returned for region based on order of priority)
Category = c (rule ids returned for category based on order of priority)
Property = p (rule ids returned for properties)

  rule_ids = r intersect c intersect p
if not rule_ids:
  rule_ids1 = check for region global (country -> state -> city -> area -> pincode) : order of priority
  rule_ids2 = check for category global (main_category -> sub_category -> vertical -> product_id) : order of priority
  rule_ids = rule_ids1 + rule_ids2
```

If there is a single rule id, discount corresponding to the rule is returned. If there are multiple rules, boost parameter is used to decide which rule's discount takes preference. There is also a provision to combine rules which can be defined at the campaign level 

Persistent information is stored in mongodb and once the sale is started, it flushes redis and builds rules with discounting information in queryable format for faster response in redis (FYI : This is a one time process which takes time since it fetches all campaigns, rules from mongodb and populates redis). Post that all reads of discount calculation happens from redis

# What is not solved

1. This module doesnt serve as a caching engine of discounts for given input. It is upto to the caller to cache information of discount against product id
2. Edits requires re building redis information and can be a bit time consuming. So this project is not meant for real time edits but its  meant for real time calculation of discounts
3. Collision of names not handled. Ideal way would be each of category and region should be represented by a unique integer while creating rules.
