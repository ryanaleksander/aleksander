package controllers

import play.api.mvc._
import java.sql.Date
import play.api.libs.json.Json
import play.api.libs.json.Writes

case class Entry(id: Int, title: String, content: String, publishedDate: Date,
								 views: Int, categories: Seq[Category])

class CreateEntry(title: String, content: String, categories: Seq[Categories])
class Entries extends Controller{
	val admin = models.Administration
	implicit val writesCategory = Json.writes[Category]
	implicit val writesEntry = Writes[Entry] {
		case entry =>
			Json.obj(
				"id" -> entry.id,
				"title" -> entry.title,
				"content" -> entry.content,
				"published-date" -> entry.publishedDate,
				"views" -> entry.views,
				"categories" -> entry.categories
			)
	}
	def list = Action {
		Ok(Json.toJson(admin.list))
	}
	def create = Action { NotImplemented }
	def details(id: Int) = Action {
		Ok(Json.toJson(admin.get(id)))
	}
	def delete(id: Int) = Action { NotImplemented }
	def update(id: Int) = Action { NotImplemented }
}
