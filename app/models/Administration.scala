package models

import java.util.concurrent.atomic.AtomicInteger
import scala.collection.concurrent.TrieMap
import controllers.Entry
import java.sql.Date

trait Administration {
	def list: Seq[Entry]
	def create(title: String, content: String): Option[Entry]
	def get(id: Int): Option[Entry]
	def update(id: Int, title: String, content: String, publishedDate: Date, views: Int): Option[Entry]
	def delete(id: Int): Boolean
}

object Administration extends Administration{
	private val entries = TrieMap.empty[Int, Entry]
	private val seq = new AtomicInteger

	def list: Seq[Entry] = entries.values.to[Seq]
	def create(title: String, content: String): Option[Entry] = {
		val id = seq.incrementAndGet
		val publishedDate = new Date(new java.util.Date().getTime)
		val entry = new Entry(id, title, content, publishedDate, 0)
		Some(entry)
	}

	def get(id: Int): Option[Entry] = entries.get(id)

	def update(id: Int, title: String, content: String, publishedDate: Date, views: Int): Option[Entry] = {
		val entry = Entry(id, title, content, publishedDate, views)
		entries.replace(id, entry)
		Some(entry)
	}

	def delete(id: Int): Boolean = entries.remove(id).isDefined
}
