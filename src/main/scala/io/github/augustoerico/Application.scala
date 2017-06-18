package io.github.augustoerico

import io.vertx.core.Vertx
import io.vertx.ext.web.{Router, RoutingContext}

object Application extends App {

  override def main(args: Array[String]) = {
    println("Starting application")

    val vertx = Vertx.vertx()
    val server = vertx.createHttpServer()

    val router = Router.router(vertx)

    router.get("/hello").handler((context: RoutingContext) => {
      println(s"[${context.request().method()}] ${context.request().absoluteURI()}")

      val response = context.response()
      response.setStatusCode(200).end("Hello!")
    })


    router.get("/bye").handler((context: RoutingContext) => {
      println(s"[${context.request().method()}] ${context.request().absoluteURI()}")

      val response = context.response()
      response.setStatusCode(200).end("Bye!")
    })

    server.requestHandler(router.accept _).listen(8080, "localhost")

  }

}
