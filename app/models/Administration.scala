package models

import java.util.concurrent.atomic.AtomicInteger
import scala.collection.concurrent.TrieMap
import controllers.{Category, Entry}
import java.sql.Date

// Administration operations
trait Administration {
	// Entry operations
	def list: Seq[Entry]
	def create(title: String, content: String, categories: Seq[Category]): Option[Entry]
	def get(id: Int): Option[Entry]
	def update(id: Int, title: String, content: String, publishedDate: Date,
						 views: Int, categories: Seq[Category]): Option[Entry]
	def delete(id: Int): Boolean

	// Category operations
	def categoryList: Seq[Category]
	def categoryGet(name: String): Option[Category]
	def categoryDelete(name: String): Boolean
	def categoryCreate(name: String): Option[Category]
}

object Administration extends Administration{
	private val entries = TrieMap.empty[Int, Entry]
	private val seq = new AtomicInteger
	private val categories = TrieMap.empty[String, Category]

	def list: Seq[Entry] = entries.values.to[Seq]
	def create(title: String, content: String, categories: Seq[Category]): Option[Entry] = {
		val id = seq.incrementAndGet
		val publishedDate = new Date(new java.util.Date().getTime)
		val entry = new Entry(id, title, content, publishedDate, 0, categories)
		Some(entry)
	}

	def get(id: Int): Option[Entry] = entries.get(id)

	def update(id: Int, title: String, content: String, publishedDate: Date,
						 views: Int, categories: Seq[Category]): Option[Entry] = {
		val entry = Entry(id, title, content, publishedDate, views, categories)
		entries.replace(id, entry)
		Some(entry)
	}

	def delete(id: Int): Boolean = entries.remove(id).isDefined
	def categoryList = categories.values.toSeq
	def categoryGet(name: String) = categories.get(name)
	def categoryDelete(name: String) = categories.remove(name).isDefined
	def categoryCreate(name: String) = {
		val category = categories getOrElseUpdate(name, Category(name))
		Some(category)
	}
}
