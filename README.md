# RuleEngine

A generalized promotion rule engine for products which allows to define rules for discounting without requiring a code change

# Technologies used

1. Scala
2. MongoDB(Casbah)
3. Redis

# Overview

Basic idea behind building this rule engine is to create a minimal module to define rules for ecommerce products over dimensions such as region, category and product specific properties which is used as a criteria to calculate discounts.

Hierarchy

Sample Rule Schema

```bash
{
  "id": "<rule id>", // Generated for creation and can be used for updation,
  "name": "<rule name>",
  "description": "<rule description>",
  "version": "<rule version>", // old rules are audited
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

Given a product with information on buyer's region and product properties (only relevant ones) and category information, it fetches all rules which match region (based on priority mentioned above), which match category (based on priority mentioned above) and properties. and finds a intersection of all rule ids from the above three matches (only non empty sets are used). If there is a single rule id, discount corresponding to the rule is returned. If there are multiple rules, boost parameter is used to decide which rule's discount takes preference. There is also a provision to combine rules which can be defined at the campaign level 


