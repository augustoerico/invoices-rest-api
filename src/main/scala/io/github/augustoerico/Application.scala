package io.github.augustoerico

import io.vertx.scala.core.Vertx
import io.vertx.scala.core.http.HttpServerRequest

object Application extends App {

  override def main(args: Array[String]) = {
    println("Starting application")

    val vertx = Vertx.vertx()
    vertx
      .createHttpServer()
        .requestHandler((request: HttpServerRequest) => {
          println("Request received")
          request.response().end("Hello, world!")
        })
      .listen(8080, "localhost")

  }

}
