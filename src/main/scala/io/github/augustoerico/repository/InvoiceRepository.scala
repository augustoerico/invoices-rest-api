package io.github.augustoerico.repository

import io.github.augustoerico.models.Invoice
import io.vertx.core.{AsyncResult, Handler, Vertx}
import io.vertx.core.json.JsonObject
import io.vertx.ext.mongo.MongoClient

class Repository(vertx: Vertx) {

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

  def save(invoice: Invoice, handler: Handler[AsyncResult[String]]): Unit = {
    client.save(collection, invoice.toJsonObject, handler)
  }

  def find(query: JsonObject, handler: Handler[AsyncResult[java.util.List[JsonObject]]]) : Unit = {
    client.find(collection, query, handler)
  }
}
