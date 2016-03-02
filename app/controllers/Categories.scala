package controllers

import play.api.mvc._
import play.api.libs.json.Json

class CreateCategory(name: String)

case class Category(name: String)

class Categories extends Controller {
	implicit val writesCategory = Json.writes[Category]
	val admin = models.Administration

	def list = Action {
		Ok(Json.toJson(admin.categoryList))
	}

	def details(name: String) = Action {
		Ok(Json.toJson(admin.categoryGet(name)))
	}
}
