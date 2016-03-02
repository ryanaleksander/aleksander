package controllers

import play.api.mvc._
import java.sql.Date

case class Entry(id: Int, title: String, content: String, publishedDate: Date, views: Int)

class Entries extends Controller{
	val admin = models.Administration
	def list = Action { 	NotImplemented 	}
	def create = Action { NotImplemented }
	def details(id: Int) = Action { NotImplemented	}
	def delete(id: Int) = Action { NotImplemented }
	def update(id: Int) = Action { NotImplemented }
}
