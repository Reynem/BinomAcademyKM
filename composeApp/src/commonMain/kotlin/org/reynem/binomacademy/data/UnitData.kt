package org.reynem.binomacademy.data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

@Serializable
data class UnitData(
    val id: Int,
    val lessonId: Int,
    val title: String,
    val stories: List<String>,
    @Serializable(with = AssignmentListSerializer::class)
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

object AssignmentListSerializer : KSerializer<List<Assignment>> by
ListSerializer(Assignment.serializer())