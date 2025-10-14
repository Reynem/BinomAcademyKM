package org.reynem.binomacademy.data

import kotlinx.serialization.Serializable

@Serializable
data class UnitData(
    val topicId: Int,
    val id: Int,
    val lessonId: Int,
    val title: String,
    val stories: List<String>,
    val assignments: List<Assignment>,
    var isCompleted: Boolean = false,
    var totalAttempts: Int = 0,
    var completedAssignments: List<String> = emptyList()
) {
    fun completeAssignment(assignmentId: String): Boolean {
        if (assignments.any { it.id == assignmentId } && !completedAssignments.contains(assignmentId)) {
            completedAssignments = completedAssignments + assignmentId
            return true
        }
        return false
    }
}