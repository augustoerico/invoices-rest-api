package io.github.augustoerico.handlers.results

import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext

class FailureHandler(context: RoutingContext) {

  def handle(throwable: Throwable) {
    throwable.printStackTrace()
    context.response().setStatusCode(500).end(
      new JsonObject().put("message", throwable.getMessage).encodePrettily()
    )
  }

}
