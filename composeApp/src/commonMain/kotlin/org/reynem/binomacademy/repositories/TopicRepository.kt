package org.reynem.binomacademy.repositories

import androidx.compose.ui.util.fastForEach
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.reynem.binomacademy.data.Lesson
import org.reynem.binomacademy.data.Topic
import java.io.File

class TopicRepository (private val storageFile: File) {
    private val json = Json {
        prettyPrint = true
        classDiscriminator = "type"
    }
    private var topics: MutableList<Topic> = mutableListOf()

    init {
        if (storageFile.exists()) {
            val content = storageFile.readText()
            if (content.isNotBlank()) {
                try {
                    topics = json.decodeFromString(content)
                } catch (e: SerializationException) {
                    throw Throwable("ERROR IN TopicRepository: $e")
                }
            }
        } else {
            this.add(
                Topic(
                    id = 1, title = "Mathematics", lessons = listOf(
                        Lesson(
                            topicId = 1, id = 1, title = "Addition", units = listOf(),
                        )
                    )
                )
            )
        }
    }

    fun getAll(): List<Topic> { return topics }
    fun getById(id: Int): Topic? { return topics.find { topic -> topic.id == id}}
    fun getLength(): Int {
        var length = 0
        topics.fastForEach {
            topic -> topic.lessons.fastForEach {
                lesson -> lesson.units.fastForEach { unit ->
                    length += unit.assignments.size
                }
            }
        }
        return length
    }

    fun add(topic: Topic) {
        if (topics.any {it.id == topic.id}) {
            throw IllegalArgumentException("This topic already exists")
        }
        topics.add(topic)
        persist()
    }

    fun update(topic: Topic) {
        val index = topics.indexOfFirst { it.id == topic.id }
        if (index == -1) {
            throw NoSuchElementException("Topic with id=${topic.id} not found")
        }
        topics[index] = topic
        persist()
    }

    fun delete(id: Int) {
        val removed = topics.removeIf { it.id == id }
        if (removed) persist()
    }

    private fun persist() {
        storageFile.writeText(json.encodeToString(topics))
    }
}