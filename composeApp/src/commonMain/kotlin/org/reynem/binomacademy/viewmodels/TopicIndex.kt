package org.reynem.binomacademy.viewmodels

import org.reynem.binomacademy.data.Topic

class TopicIndex {
    var parentById = mutableMapOf<String, String>()
    var childById = mutableMapOf<String, MutableList<String>>()

    fun buildIndexes(topics: List<Topic>) {
        topics.forEach { topic ->
            val topicKey = "topic:${topic.id}"

            topic.lessons.forEach { lesson ->
                val lessonKey = "lesson:${lesson.id}_${topic.id}"
                parentById[lessonKey] = topicKey
                childById.getOrPut(topicKey) { mutableListOf() }.add(lessonKey)

                lesson.units.forEach { unit ->
                    val unitKey = "unit:${unit.id}_${lesson.id}_${topic.id}"
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

    fun getParent(id: String): String? = parentById[id]

    fun getChildren(id: String): List<String> = childById[id] ?: emptyList()

    // using loop to get chain of parents
    fun getParentChain(id: String): List<String> {
        val chain = mutableListOf<String>()
        var current = id
        while (true) {
            val parent = parentById[current] ?: break
            chain.add(parent)
            current = parent
        }
        return chain
    }

    // using loop to get chain of children
    fun getDescendants(id: String): List<String> {
        val descendants = mutableListOf<String>()
        val queue = ArrayDeque<String>()
        queue.add(id)
        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            val children = childById[current] ?: continue
            descendants.addAll(children)
            queue.addAll(children)
        }
        return descendants
    }

    fun areAllAssignmentsCompleted(parentId: String, completedAssignments: Set<String>): Boolean {
        val descendants = getDescendants(parentId)
        val assignments = descendants.filter { it.startsWith("assignment:") }
        return assignments.all { it.removePrefix("assignment:") in completedAssignments }
    }
}