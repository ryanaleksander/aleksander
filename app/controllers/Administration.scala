package controllers

import play.api.mvc._

class Administration extends Controller {
	def admin = Action {
		Ok
	}
	def login(username: String, password: String) = Action {
		Ok
	}
	def createEntry(entry: String) = Action {
		Ok
	}
}
