package io.github.augustoerico.models

import java.util.Calendar

import io.vertx.lang.scala.json.JsonObject

class Invoice(
               val customerId: String,
               val addressId: String,
               val _type: String,
               val amount: Double,
               val createdAt: Long
             ) {

  if (customerId == null || customerId.isEmpty) {
    val message = "[customerId] is required"
    throw new IllegalArgumentException(message)
  }

  if (addressId == null || addressId.isEmpty) {
    val message = "[addressId] is required"
    throw new IllegalArgumentException(message)
  }

  if (amount < 0) {
    val message = s"[amount] should be greater than or equals to to 0.0; provided: [$amount]"
    throw new IllegalArgumentException(message)
  }

  if (createdAt < 0) {
    val message = s"[createdAt should be greater than or equals to 0.0; provided: [$createdAt]"
    throw new IllegalArgumentException(message)
  }

  def this(
            customerId: String,
            addressId: String,
            _type: String = "shop",
            amount: Double = 0.0
          ) = this(customerId, addressId, _type, amount, Calendar.getInstance().getTimeInMillis)

  def this(json: JsonObject) = this(
    json.getString("customerId"),
    json.getString("addressId"),
    json.getString("type", "shop"),
    json.getDouble("amount", 0.00)
  )

  def toJsonObject: JsonObject = {
    new JsonObject().put("customerId", customerId)
      .put("addressId", addressId)
      .put("type", _type)
      .put("amount", amount)
      .put("createdAt", amount)
  }

}
