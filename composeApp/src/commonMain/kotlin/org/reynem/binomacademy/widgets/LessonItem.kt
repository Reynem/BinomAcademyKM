package org.reynem.binomacademy.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.reynem.binomacademy.LocalAppState
import org.reynem.binomacademy.data.Lesson
import org.reynem.binomacademy.viewmodels.LocalAssignmentViewModel

@Composable
fun LessonItem(lesson: Lesson) {
    val appState = LocalAppState.current
    val assignmentViewModel = LocalAssignmentViewModel.current
    Card (
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        colors = CardColors(
            containerColor =
                if (assignmentViewModel.checkLesson(
                    lesson.topicId.toString(),
                    lesson.id.toString())
                    )   MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.surfaceContainer
            ,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = MaterialTheme.colorScheme.onSurface
        ),
    ){
        Row (
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(lesson.title)
            Button(
                onClick = { appState.navigateToLesson(lesson = lesson) },
                contentPadding = PaddingValues(
                    start = 20.dp,
                    top = 2.dp,
                    end = 20.dp,
                    bottom = 2.dp,
                )
            ) {
                Text("Start")
            }
        }
    }
}