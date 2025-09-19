package org.reynem.binomacademy.widgets

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.reynem.binomacademy.data.Lesson
import org.reynem.binomacademy.data.Topic

@Composable
fun MainBody(){
    val topic1 = Topic(
        id = 1,
        title = "Lesson",
        lessons = listOf(Lesson(topicId = 1, id = 1, title = "Morty", units = listOf()))
    )
    val topic2 = Topic(
        id = 2,
        title = "Lesson 2",
        lessons = listOf(Lesson(topicId = 1, id = 1, title = "Morty", units = listOf()))
    )
    val topic3 = Topic(
        id = 3,
        title = "Lesson 3",
        lessons = listOf(Lesson(topicId = 1, id = 1, title = "Morty", units = listOf()))
    )
    Card (
        modifier = Modifier
            .padding(vertical = 12.dp, horizontal = 8.dp)
            .fillMaxSize(),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = MaterialTheme.colorScheme.onSurface
        ),
    ){
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(listOf(topic1,topic2,topic3)) {
                topic ->
                TopicCard(topic)
            }
        }
    }
}