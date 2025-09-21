package org.reynem.binomacademy

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.reynem.binomacademy.data.TopicRepository

import org.reynem.binomacademy.widgets.AppHeader
import org.reynem.binomacademy.widgets.MainBody
import org.reynem.binomacademy.widgets.SideNavBar
import java.io.File

@Composable
@Preview
fun App() {
    val topics = TopicRepository(File("topics.json"))
    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            AppHeader()
            Row {
                SideNavBar()
                MainBody(topics)
            }
        }
    }
}