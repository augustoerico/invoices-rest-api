package io.github.augustoerico.handlers

import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext

class InfoHandler extends Handler[RoutingContext] {

  override def handle(context: RoutingContext): Unit = {
    println(s"[${context.request().method()}] ${context.request().absoluteURI()}")
    context.next()
  }

}

