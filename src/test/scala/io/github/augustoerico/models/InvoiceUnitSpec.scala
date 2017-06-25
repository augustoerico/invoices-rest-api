package io.github.augustoerico.models

import org.scalatest.{FlatSpec, Matchers}

class InvoiceUnitSpec extends FlatSpec with Matchers {

  "An Invoice" should "be created for parameters" in {
    val invoice = new Invoice("1", "2", "type", 1.0, 0)
    invoice.customerId should be ("1")
    invoice.addressId should be ("2")
    invoice._type should be ("type")
    invoice.amount should be (1.0)
    invoice.createdAt should be (0)
  }

}
