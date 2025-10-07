package org.reynem.binomacademy.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import binomacademy.composeapp.generated.resources.Res
import binomacademy.composeapp.generated.resources.book_assignment
import binomacademy.composeapp.generated.resources.book_story
import binomacademy.composeapp.generated.resources.book_tip
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.reynem.binomacademy.data.Assignment
import org.reynem.binomacademy.data.Lesson
import org.reynem.binomacademy.file_manager.LocalProfileManager
import org.reynem.binomacademy.theme.backgroundDark
import org.reynem.binomacademy.viewmodels.AssignmentModelFactory
import org.reynem.binomacademy.viewmodels.AssignmentViewModel
import org.reynem.binomacademy.widgets.MultipleChoiceView
import org.reynem.binomacademy.widgets.NumberInputView
import org.reynem.binomacademy.widgets.TextInputView
import org.reynem.binomacademy.widgets.TrueFalseView

@Composable
fun UnitPage(
    lesson: Lesson,
    index: Int,
    assignmentViewModel: AssignmentViewModel = viewModel(
        key = lesson.id.toString(),
        factory = AssignmentModelFactory(LocalProfileManager.current)
    )
) {
    val unit = lesson.units[index]

    val userAnswers by assignmentViewModel.userAnswers
    val completedAnswers by assignmentViewModel.completedAssignments

    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
    ) {
        Text(
            text = unit.title,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(backgroundDark)
        )

        // STORIES
        SectionHeader(icon = Res.drawable.book_story, label = "Stories")
        if (unit.stories.isEmpty()) {
            EmptyCard("No stories yet")
        } else {
            unit.stories.forEach { story ->
                ItemCard(content = story)
            }
        }

        // ASSIGNMENTS
        SectionHeader(icon = Res.drawable.book_assignment, label = "Assignments")
        if (unit.assignments.isEmpty()) {
            EmptyCard("No assignments yet")
        } else {
            unit.assignments.forEach { assignment ->
                val isCompleted = assignment.id in completedAnswers
                val currentAnswer = userAnswers[assignment.id]

                AssignmentCard(
                    assignment = assignment,
                    isCompleted = isCompleted,
                    currentAnswer = currentAnswer,
                    onAnswerChanged = { answer ->
                        assignmentViewModel.updateAnswer(assignment.id, answer)
                    }
                )
            }
        }

        Button(
            onClick = {
                assignmentViewModel.checkAssignments(unit.assignments) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Check answers")
        }

        // TIPS
        SectionHeader(icon = Res.drawable.book_tip, label = "Tips")
        EmptyCard("Coming soon...")
    }
}

@Composable
private fun SectionHeader(icon: DrawableResource, label: String) {
    Row(
        modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp)
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = label,
            modifier = Modifier.size(32.dp)
        )
        Text(
            text = label,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
private fun ItemCard(content: String) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Text(
            text = content,
            fontSize = 16.sp,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
private fun AssignmentCard(
    assignment: Assignment,
    isCompleted: Boolean,
    currentAnswer: Any?,
    onAnswerChanged: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCompleted) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = assignment.title,
                fontSize = 16.sp,
                fontWeight = if (isCompleted) FontWeight.Medium else FontWeight.Normal,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))
            when (assignment) {
                is Assignment.MultipleChoice -> MultipleChoiceView(assignment, currentAnswer as String?, onAnswerChanged)
                is Assignment.TextInput -> TextInputView(assignment, isCompleted, currentAnswer as String?, onAnswerChanged)
                is Assignment.TrueFalse -> TrueFalseView(assignment, currentAnswer as String?, onAnswerChanged)
                is Assignment.NumberInput -> NumberInputView(assignment, isCompleted, currentAnswer as String?, onAnswerChanged)
            }

            if (isCompleted) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "✓ Completed",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyCard(text: String) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            modifier = Modifier.padding(12.dp)
        )
    }
}