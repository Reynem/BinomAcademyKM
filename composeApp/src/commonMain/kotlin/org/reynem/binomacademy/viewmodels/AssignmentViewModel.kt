package org.reynem.binomacademy.viewmodels

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import org.reynem.binomacademy.data.Assignment
import org.reynem.binomacademy.file_manager.ProfileManager
import org.reynem.binomacademy.utils.normalizeNumberInput
import org.reynem.binomacademy.utils.numberFormat
import kotlin.reflect.KClass


val LocalAssignmentViewModel = compositionLocalOf<AssignmentViewModel> {
    error("LocalAssignmentViewModel not provided")
}

class AssignmentViewModel(
    private val profileManager: ProfileManager,
    private val topicIndex: TopicIndex
    ) : ViewModel() {
    private val _userAnswers = mutableStateOf<Map<String, Any?>>(emptyMap())
    val userAnswers = _userAnswers

    private val _completedAssignments = mutableStateOf<Set<String>>(emptySet())
    val completedAssignments = _completedAssignments

    fun updateAnswer(assignmentId: String, answer: Any?) {
        _userAnswers.value = _userAnswers.value.toMutableMap().apply {
            this[assignmentId] = answer
        }
    }

    fun isParentCompleted(parentId: String) : Boolean{
        return topicIndex.areAllAssignmentsCompleted(parentId, _completedAssignments.value)
    }

    fun checkAssignments(unitAssignments: List<Assignment>) {
        val newlyCompleted = mutableSetOf<String>()

        if (unitAssignments.isEmpty()) {
            return
        }

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

            if (isCorrect && !profileManager.user.value.completedAssignments.contains(assignment.id)) {
                newlyCompleted.add(assignment.id)
                profileManager.updateUser { copy(
                    completedAssignmentsTotal = this.completedAssignmentsTotal + 1,
                    completedAssignments = this.completedAssignments + newlyCompleted
                ) }
            } else if (isCorrect && !_completedAssignments.value.contains(assignment.id)){
                newlyCompleted.add(assignment.id)
            }
        }

        _completedAssignments.value += newlyCompleted

        val firstCompleted = newlyCompleted.firstOrNull() ?: return

        val parent = topicIndex.getParent("assignment:${firstCompleted}") ?: return
        if (isParentCompleted(parent) && !profileManager.user.value.completedUnits.contains(parent)) {
            profileManager.updateUser { copy(
                completedUnits = this.completedUnits + parent,
                completedUnitsTotal = this.completedUnitsTotal + 1
            ) }
        }
    }

    fun checkLesson(topicId: String, lessonId: String) : Boolean {
        val children = topicIndex.getChildren("lesson:${lessonId}_${topicId}")
        val isCompleted = children.all { topicIndex.areAllAssignmentsCompleted(it, profileManager.user.value.completedAssignments) }
        return isCompleted
    }

//    fun clearAnswers() {
//        _userAnswers.value = emptyMap()
//    }
}

class AssignmentModelFactory(
    private val profileManager: ProfileManager,
    private val topicIndex: TopicIndex
) :
    ViewModelProvider.NewInstanceFactory()

    {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T =
            AssignmentViewModel(profileManager = profileManager, topicIndex = topicIndex) as T
    }