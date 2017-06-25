package io.github.augustoerico.models

import io.vertx.core.json.JsonObject
import org.scalatest.{FlatSpec, Matchers}

class InvoiceUnitSpec extends FlatSpec with Matchers {

  "An invoice" should "be created with given parameters" in {
    val invoice = new Invoice("1", "2", "type", 1.0, 0)

    invoice.customerId should be ("1")
    invoice.addressId should be ("2")
    invoice._type should be ("type")
    invoice.amount should be (1.0)
    invoice.createdAt should be (0)
  }

  "An invoice" should "be created with default parameters" in {
    val invoice = new Invoice("1", "2")

    invoice.customerId should be ("1")
    invoice.addressId should be ("2")
    invoice._type should be ("shop")
    invoice.amount should be (0.0)
    invoice.createdAt should be >= 0L
  }

  "An exception" should "be thrown for missing customer ID" in {
    a [IllegalArgumentException] should be thrownBy { new Invoice("", "2") }
  }

  "An exception" should "be thrown for missing address ID" in {
    a [IllegalArgumentException] should be thrownBy { new Invoice("1", "") }
  }

  "An exception" should "be thrown for invalid amount" in {
    a [IllegalArgumentException] should be thrownBy { new Invoice("1", "2", "type", -3.0) }
  }

  "An exception" should "be throw for invalid creation time" in {
    a [IllegalArgumentException] should be thrownBy { new Invoice("1", "2", "type", 1.0, -10L) }
  }

  "An invoice" should "be created from a valid JSON" in {
    val json = new JsonObject()
      .put("customerId", "1")
      .put("addressId", "2")
      .put("type", "type")
      .put("amount", 10.0)
    val invoice = new Invoice(json)

    invoice.customerId should be ("1")
    invoice.addressId should be ("2")
    invoice._type should be ("type")
    invoice.amount should be (10.0)
    invoice.createdAt should be >= 0L
  }

  "A JSON" should "be returned from an invoice" in {
    val invoice = new Invoice("1", "2")
    val json = invoice.toJsonObject

    json shouldBe a [JsonObject]
    json.getString("customerId") should be ("1")
    json.getString("addressId") should be ("2")
    json.getString("type") should be ("shop")
    json.getDouble("amount") should be (0.0)
    json.getLong("createdAt").longValue() should be >= 0L

  }

}