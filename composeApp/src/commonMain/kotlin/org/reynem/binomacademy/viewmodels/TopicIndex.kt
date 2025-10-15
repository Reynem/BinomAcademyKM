package org.reynem.binomacademy.viewmodels

import org.reynem.binomacademy.data.Topic

class TopicIndex {
    var parentById = mutableMapOf<String, String>()
    var childById = mutableMapOf<String, MutableList<String>>()

    fun buildIndexes(topics: List<Topic>) {
        topics.forEach { topic ->
            val topicKey = "topic:${topic.id}"

            topic.lessons.forEach { lesson ->
                val lessonKey = "lesson:${lesson.id}"
                parentById[lessonKey] = topicKey
                childById.getOrPut(topicKey) { mutableListOf() }.add(lessonKey)

                lesson.units.forEach { unit ->
                    val unitKey = "unit:${unit.id}"
                    parentById[unitKey] = lessonKey
                    childById.getOrPut(lessonKey) { mutableListOf() }.add(unitKey)

                    unit.assignments.forEach { assignment ->
                        val assignmentKey = "assignment:${assignment.id}"
                        parentById[assignmentKey] = unitKey
                        childById.getOrPut(unitKey) { mutableListOf() }.add(assignmentKey)
                    }
                }
            }
        }
    }
}
