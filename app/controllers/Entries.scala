package controllers

import play.api.mvc._
import java.sql.Date

case class Entry(id: Int, title: String, content: String, publishedDate: Date, views: Int)

class Entries extends Controller{

}
