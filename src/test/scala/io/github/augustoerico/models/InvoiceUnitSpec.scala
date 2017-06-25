package io.github.augustoerico.models

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

}