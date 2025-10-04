package org.reynem.binomacademy.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import org.reynem.binomacademy.data.Assignment

class AssignmentViewModel : ViewModel() {
    private val _userAnswers = mutableStateOf<Map<String, Any?>>(emptyMap())
    val userAnswers = _userAnswers

    private val _completedAssignments = mutableStateOf<Set<String>>(emptySet())
    val completedAssignments = _completedAssignments

    fun updateAnswer(assignmentId: String, answer: Any?) {
        _userAnswers.value = _userAnswers.value.toMutableMap().apply {
            this[assignmentId] = answer
        }
    }

    fun checkAssignments(unitAssignments: List<Assignment>) {
        val newlyCompleted = mutableSetOf<String>()

        for (assignment in unitAssignments) {
            val userAnswer = _userAnswers.value[assignment.id]
            val isCorrect = when (assignment) {
                is Assignment.MultipleChoice -> userAnswer == assignment.correctAnswer
                is Assignment.TextInput -> userAnswer == assignment.correctAnswer
                is Assignment.TrueFalse -> userAnswer == assignment.correctAnswer
                is Assignment.NumberInput -> userAnswer == assignment.correctAnswer.toString()
            }

            if (isCorrect) {
                newlyCompleted.add(assignment.id)
            }
        }

        _completedAssignments.value += newlyCompleted
    }

//    fun clearAnswers() {
//        _userAnswers.value = emptyMap()
//    }
}