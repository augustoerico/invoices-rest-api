package io.github.augustoerico.models

import java.util.Calendar

import io.vertx.lang.scala.json.JsonObject

class Invoice(
               val customerId: String,
               val addressId: String,
               val _type: String,
               val amount: Double,
               val createdAt: Long,
               var _id: String
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
            amount: Double = 0.0,
            _id: String = ""
          ) = this(customerId, addressId, _type, amount, Calendar.getInstance().getTimeInMillis, _id)

  def this(json: JsonObject) = this(
    json.getString("customerId", null),
    json.getString("addressId", null),
    json.getString("type", "shop"),
    json.getDouble("amount", 0.0),
    json.getString("_id", "")
  )

  def toJsonObject: JsonObject = {
    val json = new JsonObject().put("customerId", customerId)
      .put("addressId", addressId)
      .put("type", _type)
      .put("amount", amount)
      .put("createdAt", createdAt)

    if (_id != null && !_id.isEmpty) {
      json.put("_id", _id)
    }

    json
  }

}
