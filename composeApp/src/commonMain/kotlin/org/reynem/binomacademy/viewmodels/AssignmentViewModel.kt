package org.reynem.binomacademy.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import org.reynem.binomacademy.data.Assignment
import org.reynem.binomacademy.utils.normalizeNumberInput
import org.reynem.binomacademy.utils.numberFormat


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
            val isCorrect = when (assignment) {
                is Assignment.NumberInput -> {
                    val rawUserInput = _userAnswers.value[assignment.id] as? String ?: continue
                    val normalizedUserInput = normalizeNumberInput(rawUserInput) ?: continue

                    val normalizedCorrect = numberFormat.format(assignment.correctAnswer)

                    normalizedUserInput == normalizedCorrect
                }

                is Assignment.MultipleChoice -> {
                    val userAnswer = _userAnswers.value[assignment.id] as? String ?: continue
                    userAnswer == assignment.correctAnswer
                }

                is Assignment.TextInput -> {
                    val userAnswer = _userAnswers.value[assignment.id] as? String ?: continue
                    userAnswer == assignment.correctAnswer
                }

                is Assignment.TrueFalse -> {
                    val userAnswer = _userAnswers.value[assignment.id] as? String ?: continue
                    userAnswer == assignment.correctAnswer.toString()
                }
            }

            if (isCorrect && !_completedAssignments.value.contains(assignment.id)) {
                newlyCompleted.add(assignment.id)
            }
        }

        _completedAssignments.value += newlyCompleted
    }

//    fun clearAnswers() {
//        _userAnswers.value = emptyMap()
//    }
}