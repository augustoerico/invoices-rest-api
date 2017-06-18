package io.github.augustoerico

import java.text.SimpleDateFormat
import java.util.Calendar

import scala.collection.immutable.Map

import io.vertx.core.Vertx
import io.vertx.ext.web.{Router, RoutingContext}

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



    server.requestHandler(router.accept _).listen(8080, "localhost")

  }

}
