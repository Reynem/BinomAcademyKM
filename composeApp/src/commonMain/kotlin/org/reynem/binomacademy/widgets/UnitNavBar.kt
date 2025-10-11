package org.reynem.binomacademy.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.reynem.binomacademy.LocalAppState
import org.reynem.binomacademy.data.Lesson
import org.reynem.binomacademy.repositories.TopicRepository
import org.reynem.binomacademy.theme.onSecondaryContainerLight
import org.reynem.binomacademy.theme.secondaryContainerLight


@Composable
fun UnitNavBar(topics: TopicRepository, lesson: Lesson, onSelect: (Int) -> Unit) {
    val appState = LocalAppState.current
    Card (
        modifier = Modifier
            .padding(vertical = 12.dp, horizontal = 8.dp)
            .fillMaxHeight()
            .fillMaxWidth(fraction = 0.15f),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = MaterialTheme.colorScheme.onSurface
        ),
    ){
        Column {
            Card (
                colors = CardDefaults.cardColors(
                    containerColor = secondaryContainerLight,
                    contentColor = onSecondaryContainerLight
                ),
                modifier = Modifier.clickable(
                    onClick = {
                        appState.backToMain()
                    }
                )
            ){
                Text(
                    text = topics.getById(lesson.topicId)!!.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 8.dp)
                )
            }

            Text(
                text = lesson.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 8.dp)
            )

            lesson.units.forEachIndexed { index, data ->
                Button(
                    onClick = { onSelect(index) },
                    shape = RoundedCornerShape(25),
                    colors = ButtonColors(
                        containerColor = if (appState.selectedUnitIndex == index)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = if (appState.selectedUnitIndex == index)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onSecondaryContainer,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(data.title)
                }
            }
        }
    }
}