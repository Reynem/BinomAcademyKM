package org.reynem.binomacademy.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.reynem.binomacademy.repositories.TopicRepository
import org.reynem.binomacademy.widgets.TopicCard

@Composable
fun MainBody(topics: TopicRepository){
    Card (
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = MaterialTheme.colorScheme.onSurface
        ),
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ){
            Text(
                text = "My Topics",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )

            @Suppress("COMPOSE_APPLIER_CALL_MISMATCH")
            BoxWithConstraints {
                var countOfColumns = 1
                if (maxWidth > 900.dp) {
                    countOfColumns = 3
                } else if (maxWidth > 500.dp) {
                    countOfColumns = 2
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(countOfColumns),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(topics.getAll()) { topic ->
                        TopicCard(topic)
                    }
                }
            }
        }
    }
}