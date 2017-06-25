package io.github.augustoerico.handlers

import java.text.SimpleDateFormat
import java.util.Calendar

import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext
import io.vertx.lang.scala.json.JsonObject

class HealthHandler extends Handler[RoutingContext] {

  override def handle(context: RoutingContext): Unit = {
    println(s"[${context.request().method()}] ${context.request().absoluteURI()}")

    val formattedDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime)
    val data = new JsonObject()
      .put("status", "OK")
      .put("dateTime", formattedDateTime)

    context.response().setStatusCode(200).end(data.encodePrettily())
  }

}
