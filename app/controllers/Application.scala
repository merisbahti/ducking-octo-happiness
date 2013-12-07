package controllers

import play.api._
import play.api.mvc._
import play.api.libs._

import reactivemongo.api._
import reactivemongo.bson._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.collection._

object Application extends Controller {


  def index = Action {
    Ok(views.html.index())
  }

  def connect() {
    import reactivemongo.api._
    import scala.concurrent.ExecutionContext.Implicits.global

    // gets an instance of the driver
    // (creates an actor system)
    val driver = new MongoDriver
    val connection = driver.connection(List("localhost"))

    // Gets a reference to the database "plugin"
    val db = connection("plugin")

    // Gets a reference to the collection "acoll"
    // By default, you get a BSONCollection.
    val collection = db("acoll")

    // Select only the documents which field 'firstName' equals 'Jack'
    val query = BSONDocument("firstName" -> "Jack")
    // select only the fields 'lastName' and '_id'
    val filter = BSONDocument(
      "lastName" -> 1,
      "_id" -> 1)

    // Get a cursor of BSONDocuments
    val cursor = collection.find(query, filter).cursor[BSONDocument]
    /* Let's enumerate this cursor and print a readable
     * representation of each document in the response */
    cursor.enumerate.apply(Iteratee.foreach { doc =>
      println("found document: " + BSONDocument.pretty(doc))
    })

    // Or, the same with getting a list
    val cursor2 = collection.find(query, filter).cursor[BSONDocument]
    val futureList: Future[List[BSONDocument]] = cursor.toList
    futureList.map { list =>
      list.foreach { doc =>
        println("found document: " + BSONDocument.pretty(doc))
      }
    }
  }
}
