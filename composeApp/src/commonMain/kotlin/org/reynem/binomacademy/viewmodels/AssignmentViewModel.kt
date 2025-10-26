package org.reynem.binomacademy.viewmodels

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateMapOf
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

    val numberOfTriesOnAssignment = mutableStateMapOf<String, Int>()

    fun updateAnswer(assignmentId: String, answer: Any?) {
        _userAnswers.value = _userAnswers.value.toMutableMap().apply {
            this[assignmentId] = answer
        }
    }

    fun isParentCompleted(parentId: String) : Boolean{
        return topicIndex.areAllAssignmentsCompleted(parentId, _completedAssignments.value)
    }

    fun checkAssignments(unitAssignments: List<Assignment>) : Boolean{
        val newlyCompleted = mutableSetOf<String>()

        if (unitAssignments.isEmpty()) {
            return false
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

            updateAssignmentTriesNumber(assignment.id)

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

        val firstCompleted = newlyCompleted.firstOrNull() ?: return false

        val parent = topicIndex.getParent("assignment:${firstCompleted}") ?: return false
        if (isParentCompleted(parent) && !profileManager.user.value.completedUnits.contains(parent)) {
            profileManager.updateUser { copy(
                completedUnits = this.completedUnits + parent,
                completedUnitsTotal = this.completedUnitsTotal + 1
            ) }
        }
        if (isParentCompleted(parent)) {
            return true
        }
        return false
    }

    fun checkLesson(topicId: String, lessonId: String) : Boolean {
        val children = topicIndex.getChildren("lesson:${lessonId}_${topicId}")
        val isCompleted = children.all { topicIndex.areAllAssignmentsCompleted(it, profileManager.user.value.completedAssignments) }
        return isCompleted
    }

    fun showCurrentProgress(wholeLength: Int) : Float {
        val currentProgress = (profileManager.user.value.completedAssignmentsTotal.toFloat() / wholeLength.toFloat())
        if (currentProgress.isNaN()){
            return 0f
        }
        return currentProgress
    }

//    fun clearAnswers() {
//        _userAnswers.value = emptyMap()
//    }

    fun clearNewlyCompleted() {
        _completedAssignments.value = emptySet()
    }

    fun getAssignmentTriesNumber(assignmentId: String) : Int {
        return numberOfTriesOnAssignment.getOrDefault(assignmentId, 1)
    }

    fun updateAssignmentTriesNumber(assignmentId: String) : Int {
        var updatedNumber = numberOfTriesOnAssignment[assignmentId] ?: 1
        updatedNumber += 1
        numberOfTriesOnAssignment[assignmentId] = updatedNumber
        return updatedNumber
    }
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