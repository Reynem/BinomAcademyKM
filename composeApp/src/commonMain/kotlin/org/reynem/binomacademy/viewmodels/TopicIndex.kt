package org.reynem.binomacademy.viewmodels

import org.reynem.binomacademy.data.Topic

class TopicIndex {
    var parentById = mutableMapOf<String, String>()

    fun buildIndexes(topics: List<Topic>) {
        topics.forEach { topic ->
            topic.lessons.forEach { lesson ->
                parentById["lesson:${lesson.id}"] = "topic:${topic.id}"
                lesson.units.forEach { unitData ->
                    parentById["unit:${unitData.id}"] = "lesson:${lesson.id}"
                    unitData.assignments.forEach { assignment ->
                        parentById["assignment:${assignment.id}"] = "assignment:${unitData.id}"
                    }
                }
            }
        }
    }
}