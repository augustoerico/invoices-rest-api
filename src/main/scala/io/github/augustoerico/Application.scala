package io.github.augustoerico

import scala.collection.JavaConverters._
import java.text.SimpleDateFormat
import java.util.Calendar

import io.github.augustoerico.repository.Repository
import io.vertx.core.json.JsonArray

import scala.collection.immutable.Map
import io.vertx.core.{AsyncResult, Vertx}
import io.vertx.ext.web.{Router, RoutingContext}
import io.vertx.lang.scala.json.JsonObject

object Application extends App {

  import org.json4s.JsonDSL._
  import org.json4s.jackson.JsonMethods._

  override def main(args: Array[String]) = {
    println("Starting application")

    val vertx = Vertx.vertx()
    val server = vertx.createHttpServer()

    val router = Router.router(vertx)

    router.get("/health").handler((context: RoutingContext) => {
      println(s"[${context.request().method()}] ${context.request().absoluteURI()}")
      val formattedDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime)
      val data = Map("status" -> "OK", "date_time" -> formattedDateTime)
      context.response().setStatusCode(200).end(compact(render(data)))
    })

    router.get("/v1/customers/:customerId/invoices").handler((context: RoutingContext) => {
      println(s"[${context.request().method()}] ${context.request().absoluteURI()}")

      val query = new JsonObject()
        .put("_id", context.request().getParam("customerId"))
        .put("month", context.request().getParam("month"))
        .put("filter", context.request().getParam("filter"))

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

    server.requestHandler(router.accept _).listen(8080, "localhost")

  }

}
