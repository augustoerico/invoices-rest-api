package io.github.augustoerico.handlers

import java.util.Calendar

import io.github.augustoerico.repository.Repository
import io.vertx.core.{AsyncResult, Handler}
import io.vertx.ext.web.RoutingContext

class CreateHandler extends Handler[RoutingContext] {

  override def handle(context: RoutingContext): Unit = {
    val request = context.request()
    val body = context.getBodyAsJson // TODO validate body

    body
      .put("customerId", request.getParam("customerId"))
      .put("addressId", request.getParam("addressId"))
      .put("createdAt", Calendar.getInstance().getTimeInMillis)

    new Repository(context.vertx()).save("invoices", body, (future: AsyncResult[String]) => {
      val response = context.response()

      if (future.succeeded()) {
        val id = future.result()
        println(s"Invoice saved with id=$id")
        response.setStatusCode(201).end(body.put("_id", id).encodePrettily())
      } else {
        future.cause().printStackTrace()
      }
    })
  }

}
