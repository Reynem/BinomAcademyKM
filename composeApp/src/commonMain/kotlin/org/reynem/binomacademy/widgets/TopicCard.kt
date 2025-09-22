package org.reynem.binomacademy.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.reynem.binomacademy.data.Topic

@Composable
fun TopicCard(topic: Topic) {
    Card (
        modifier = Modifier
            .padding(8.dp)
    ) {
        Text(
            text = topic.title,
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
        )
        Column (
            verticalArrangement = Arrangement.Top
        ){
            topic.lessons.forEach { lesson -> LessonItem(lesson = lesson) }
        }
    }
}