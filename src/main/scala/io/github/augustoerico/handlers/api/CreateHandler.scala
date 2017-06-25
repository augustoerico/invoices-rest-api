package io.github.augustoerico.handlers.api

import io.github.augustoerico.handlers.results.FailureHandler
import io.github.augustoerico.models.Invoice
import io.github.augustoerico.repository.InvoiceRepository
import io.vertx.core.Handler
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext

class CreateHandler extends Handler[RoutingContext] {

  override def handle(context: RoutingContext): Unit = {
    val body = context.getBodyAsJson

    val customerId = context.request().getParam("customerId")
    val addressId = context.request().getParam("addressId")

    val response = context.response()
    try {
      val invoice = new Invoice(
        body.put("customerId", customerId).put("addressId", addressId)
      )
      val onSuccess = (id: String) => {
        println(s"Invoice created with id = $id")
        invoice._id = id
        response.setStatusCode(201).end(invoice.toJsonObject.encodePrettily())
      }

      new InvoiceRepository(context.vertx()).save(invoice, onSuccess, new FailureHandler(context).handle _)
    } catch {
      case iae: IllegalArgumentException => response.setStatusCode(400).setStatusMessage(iae.getMessage)
      case e: Exception => new FailureHandler(context).handle(e)
    }
  }

}
