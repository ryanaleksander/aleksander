package controllers

import play.api.mvc._
import java.sql.Date
import play.api.libs.json._

case class Entry(id: Int, title: String, content: String, publishedDate: Date,
								 views: Int, categories: Seq[Category])

case class CreateEntry(title: String, content: String, categories: Seq[Category])
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

	import play.api.libs.functional.syntax._
	implicit val readsCategory: Reads[Category] = Json.reads[Category]
	implicit val readsSeqCategory: Reads[Seq[Category]] = Reads.seq(readsCategory)
	implicit val readsCreateEntry: Reads[CreateEntry] = (
		(__ \ "title").read(Reads.minLength[String](1)) and
			(__ \ "content").read(Reads.minLength[String](1)) and
			(__ \ "categories").read[Seq[Category]]
		)(CreateEntry.apply _)

	def list = Action {
		Ok(Json.toJson(admin.list))
	}
	def create = Action(parse.json) { implicit request =>
		request.body.validate[CreateEntry] match {
			case JsSuccess(createEntry, _) =>
				admin.create(createEntry.title, createEntry.content, createEntry.categories) match {
					case Some(entry) => Ok(Json.toJson(entry))
					case None => InternalServerError
				}
			case JsError(errors) => BadRequest
		}
	}
	def details(id: Int) = Action {
		Ok(Json.toJson(admin.get(id)))
	}
	def delete(id: Int) = Action { NotImplemented }
	def update(id: Int) = Action { NotImplemented }
}
