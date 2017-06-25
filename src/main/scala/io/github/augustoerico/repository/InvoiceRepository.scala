package io.github.augustoerico.repository

import io.github.augustoerico.models.Invoice
import io.vertx.core.json.JsonObject
import io.vertx.core.{AsyncResult, Vertx}
import io.vertx.ext.mongo.MongoClient

class InvoiceRepository(vertx: Vertx) {

  val collection = "invoices"

  /**
    * FIXME use env vars
    */
  val connectionString = "mongodb://invoices-user:invoices-password@ds149567.mlab.com:49567/invoices-db"
  val dbName = "invoices-db"

  val config = new JsonObject()
    .put("connection_string", connectionString)
    .put("db_name", dbName)

  val client = MongoClient.createShared(vertx, config)

  def save(invoice: Invoice, idHandler: (String) => Unit, failureHandler: (Throwable) => Unit): Unit = {

    client.save(collection, invoice.toJsonObject, (future: AsyncResult[String]) => {
      if (future.succeeded()) {
        idHandler(future.result())
      } else {
        failureHandler(future.cause())
      }
    })

  }

  def find(query: JsonObject, resultHandler: (java.util.List[JsonObject]) => Unit,
           failureHandler: (Throwable) => Unit): Unit = {

    client.find(collection, query, (future: AsyncResult[java.util.List[JsonObject]]) => {
      if (future.succeeded()) {
        resultHandler(future.result())
      } else {
        failureHandler(future.cause())
      }
    })

  }
}
