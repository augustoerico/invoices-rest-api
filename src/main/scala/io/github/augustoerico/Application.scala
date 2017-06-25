package io.github.augustoerico

import java.text.SimpleDateFormat
import java.util.Calendar

import io.github.augustoerico.handlers.ListHandler
import io.github.augustoerico.repository.Repository
import io.vertx.core.json.JsonArray
import io.vertx.core.{AsyncResult, Vertx}
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.{Router, RoutingContext}
import io.vertx.lang.scala.json.JsonObject

object Application extends App {

  override def main(args: Array[String]) = {
    println("Starting application")

    val vertx = Vertx.vertx()
    val server = vertx.createHttpServer()

    val router = Router.router(vertx)

    router.route().handler(BodyHandler.create())

    router.get("/health").handler((context: RoutingContext) => {
      println(s"[${context.request().method()}] ${context.request().absoluteURI()}")

      val formattedDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime)
      val data = new JsonObject()
        .put("status", "OK")
        .put("dateTime", formattedDateTime)

      context.response().setStatusCode(200).end(data.encodePrettily())
    })

    router.get("/v1/customers/:customerId/invoices").handler(new ListHandler().handle _)

    router.get("/v1/customers/:customerId/addresses/:addressId/invoices").handler((context: RoutingContext) => {
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

    })

    router.post("/v1/customers/:customerId/addresses/:addressId/invoices").handler((context: RoutingContext) => {
      println(s"[${context.request().method()}] ${context.request().absoluteURI()}")

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

    })

    server.requestHandler(router.accept _).listen(8080, "localhost")

  }

}