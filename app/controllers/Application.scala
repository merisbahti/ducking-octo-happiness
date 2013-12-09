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
    Ok(views.html.upload())
  }

  def testIndex() = Action {
    Ok(views.html.index())
  }

  def upload = Action(parse.multipartFormData) { request =>
    request.body.file("file").map { file =>
      import java.io._
      val uri = MongoClientURI("mongodb://localhost:27017/")
      val mongoClient =  MongoClient(uri)
      val db = mongoClient("databaseName")
      val gridfs = GridFS(db)
      val fileInputStream = new FileInputStream(file.ref.file)
      gridfs(fileInputStream) { fh =>
        fh.filename = file.filename
        fh.contentType = file.contentType.getOrElse("unknown") 
      }
      fileInputStream.close()
      file.ref.file.delete()
      mongoClient.close();
      Ok("localhost:9000/"+file.filename)
      
    }.getOrElse {
      Ok("no file");
    }
  }

  def download(ref: String) = Action {
    val uri = MongoClientURI("mongodb://localhost:27017/")
    val mongoClient =  MongoClient(uri)
    val db = mongoClient("databaseName")
    val coll = db("collectionName")
    val gfs = GridFS(db)
    val f1 = gfs findOne { "filename" $eq ref }
    mongoClient.close;
    Ok(f1.getOrElse("not found").toString)
  }

  def list() = Action {
    val uri = MongoClientURI("mongodb://localhost:27017/")
    val mongoClient =  MongoClient(uri)
    val db = mongoClient("databaseName")
    val coll = db("collectionName")
    val gfs = GridFS(db)
    val str = gfs.mkString("\n")
    mongoClient.close();
    Ok(str)
  } 

}
