package io.github.augustoerico.handlers

import io.github.augustoerico.repository.Repository
import io.vertx.core.{AsyncResult, Handler}
import io.vertx.core.json.JsonArray
import io.vertx.ext.web.RoutingContext
import io.vertx.lang.scala.json.JsonObject

class GetHandler extends Handler[RoutingContext] {

  override def handle(context: RoutingContext): Unit = {
    println(s"[${context.request().method()}] ${context.request().absoluteURI()}")

    val request = context.request()

    val query = new JsonObject()
      .put("customerId", request.getParam("customerId"))
      .put("addressId", request.getParam("addressId"))

    new Repository(context.vertx()).find("invoices", query, (future: AsyncResult[java.util.List[JsonObject]]) => {
      val response = context.response()

      if (future.succeeded()) {
        val data = new JsonArray(future.result()).encodePrettily()
        response.setStatusCode(200).end(data)
      } else {
        future.cause().printStackTrace()
      }
    })
  }

}
