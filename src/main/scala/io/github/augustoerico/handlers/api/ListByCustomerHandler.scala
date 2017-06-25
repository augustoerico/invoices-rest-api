package io.github.augustoerico.handlers.api

import io.github.augustoerico.handlers.results.FailureHandler
import io.github.augustoerico.repository.InvoiceRepository
import io.vertx.core.Handler
import io.vertx.core.json.JsonArray
import io.vertx.ext.web.RoutingContext
import io.vertx.lang.scala.json.JsonObject

class ListByCustomerHandler extends Handler[RoutingContext] {

  override def handle(context: RoutingContext): Unit = {
    val request = context.request()
    val response = context.response()

    val query = new JsonObject()
      .put("customerId", request.getParam("customerId"))
      .put("month", request.getParam("month"))
      .put("filter", request.getParam("filter"))

    val onSuccess = (result: java.util.List[JsonObject]) => {
      response.setStatusCode(200).end(new JsonArray(result).encodePrettily())
    }

    try {
      new InvoiceRepository(context.vertx()).find(query, onSuccess, new FailureHandler(context).handle _)
    } catch {
      case e: Exception => new FailureHandler(context).handle(e)
    }

  }

}
