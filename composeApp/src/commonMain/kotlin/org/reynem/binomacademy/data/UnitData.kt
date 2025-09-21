package org.reynem.binomacademy.data

import kotlinx.serialization.Serializable
import java.lang.Error

@Serializable
data class UnitData(
    val id: Int,
    val lessonId: Int,
    val title: String,
    val stories: List<String>,
    val assignments: List<String>,
    var isCompleted: Boolean,
    var totalAttempts: Int,
    val completedAssignments: List<String>
) {
    fun completeAssignment(id: Int, lessonId: Int, assignment: String): Error? {
        if (this.id == id && this.lessonId == lessonId) {
            this.completedAssignments + assignment
            return null
        }
        throw IllegalStateException("INCORRECT ASSIGNMENT")
    }
}
