package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.collection._

import play.api._
import play.api.mvc._
import play.api.libs._
import play.api.libs.iteratee._

import com.mongodb.casbah.Imports._
import com.mongodb.casbah.gridfs.Imports._
import com.novus.salat.global._

object Application extends Controller {


  def index() = Action {
    val uri = MongoClientURI("mongodb://localhost:27017/")
    val mongoClient =  MongoClient(uri)
    val db = mongoClient("databaseName")
    val coll = db("collectionName")
    val gfs = GridFS(db)

    val f1 = future { 
      gfs findOne { "contentType" $eq "text/plain" } 
    }

    val f2 = future {
      gfs findOne { "filename" $eq "licens" }
    }

    Async {
      for {
        x1 <- f1
        x2 <- f2
      } yield {

        val xs = List(x1, x2).flatten map { x =>
          x.toString + ":\n" + scala.io.Source.fromInputStream(x.inputStream).mkString("")
        }

        Ok(xs.mkString("\n\nAND\n\n"))
      }
    }
  }
}
