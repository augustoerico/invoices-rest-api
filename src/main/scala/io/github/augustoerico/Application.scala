package io.github.augustoerico

import io.github.augustoerico.handlers.{CreateHandler, GetHandler, HealthHandler, ListHandler}
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler

object Application extends App {

  override def main(args: Array[String]) = {
    println("Starting application")

    val vertx = Vertx.vertx()
    val server = vertx.createHttpServer()

    val router = Router.router(vertx)

    router.route().handler(BodyHandler.create())

    router.get("/health").handler(new HealthHandler().handle _)
    router.get("/v1/customers/:customerId/invoices").handler(new ListHandler().handle _)
    router.get("/v1/customers/:customerId/addresses/:addressId/invoices").handler(new GetHandler().handle _)
    router.post("/v1/customers/:customerId/addresses/:addressId/invoices").handler(new CreateHandler().handle _)

    server.requestHandler(router.accept _).listen(8080, "localhost")

  }

}