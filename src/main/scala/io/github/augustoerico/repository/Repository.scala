package io.github.augustoerico.repository

import io.vertx.core.{AsyncResult, Handler, Vertx}
import io.vertx.core.json.JsonObject
import io.vertx.ext.mongo.MongoClient

class Repository(vertx: Vertx) {

  /**
    * FIXME use env vars
    */
  val connectionString = "mongodb://invoices-user:invoices-password@ds149567.mlab.com:49567/invoices-db"
  val dbName = "invoices-db"

  val config = new JsonObject()
    .put("connection_string", connectionString)
    .put("db_name", dbName)

  val client = MongoClient.createShared(vertx, config)

  def save(collection: String, json: JsonObject, handler: Handler[AsyncResult[String]]): Unit = {
    client.save(collection, json, handler)
  }

  def find(collection: String, query: JsonObject, handler: Handler[AsyncResult[java.util.List[JsonObject]]]) : Unit = {
    client.find(collection, query, handler)
  }
}
