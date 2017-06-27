package io.github.augustoerico.builders

import io.vertx.lang.scala.json.JsonObject

class QueryBuilder {

  val json = new JsonObject()

  def isValid(parameter: String) = {
    parameter != null && !parameter.isEmpty
  }

  def with_Id(_id: String) = {
    if (isValid(_id)) json.put("_id", _id)
    this
  }

  def withCustomerId(customerId: String) = {
    if (isValid(customerId)) json.put("customerId", customerId)
    this
  }

  def withAddressId(addressId: String) = {
    if (isValid(addressId)) json.put("addressId", addressId)
    this
  }

  def withType(_type: String) = {
    if (isValid(_type)) json.put("type", _type)
    this
  }

  def build: (JsonObject) = {
    this.json
  }

}
