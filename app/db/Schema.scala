import slick.driver.PostgresDriver.api._
import java.sql.Date

object Schema {
	class Entries(tag: Tag) extends Table[(Int, String, String, Date, Int)](tag, "ENTRIES") {
		def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
		def title = column[String]("title")
		def content = column[String]("content")
		def publishedDate = column[Date]("published_date")
		def views = column[Int]("views")

		def * = (id, title, content, publishedDate, views)
	}

	val entries = TableQuery[Entries]

	class Categories(tag: Tag) extends Table[(String)](tag, "CATEGORIES") {
		def name = column[String]("name", O.PrimaryKey)

		def * = name
	}

	val categories = TableQuery[Categories]

	class EntryCategory(tag: Tag) extends Table[(Int, String)](tag, "ENTRY_CATEGORY") {
		def entryId = column[Int]("entry_id")
		def categoryName = column[String]("cateogry_name")

		def * = (entryId, categoryName)
		def entry = foreignKey("ENT_FK", entryId, entries)(_.id)
		def category = foreignKey("CAT_FK", categoryName, categories)(_.name)
		def pk = primaryKey("pk", (entryId, categoryName))
	}

	val entryCategory = TableQuery[EntryCategory]

	class User(tag: Tag) extends Table[(String, String, String)](tag, "USERS") {
		def username = column[String]("username", O.PrimaryKey)
		def password = column[String]("password")
		def usertype = column[String]("usertype")

		def * = (username, password, usertype)
	}
}