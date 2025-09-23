package org.reynem.binomacademy.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import binomacademy.composeapp.generated.resources.Res
import binomacademy.composeapp.generated.resources.book_assignment
import binomacademy.composeapp.generated.resources.book_story
import binomacademy.composeapp.generated.resources.book_tip
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.reynem.binomacademy.data.Lesson
import org.reynem.binomacademy.theme.backgroundDark

@Composable
fun UnitPage(lesson: Lesson, index: Int) {
    val unit = lesson.units[index]

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
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
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
                val isCompleted = unit.completedAssignments.contains(assignment)
                AssignmentCard(assignment = assignment, isCompleted = isCompleted)
            }
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
private fun AssignmentCard(assignment: String, isCompleted: Boolean) {
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
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = assignment,
                fontSize = 16.sp,
                fontWeight = if (isCompleted) FontWeight.Medium else FontWeight.Normal,
                modifier = Modifier.weight(1f)
            )
            if (isCompleted) {
                Text(
                    "✓",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp)
                )
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
