package org.reynem.binomacademy

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.reynem.binomacademy.data.Lesson
import org.reynem.binomacademy.data.TopicRepository
import org.reynem.binomacademy.screens.AppScreens
import org.reynem.binomacademy.screens.UnitNavBar
import org.reynem.binomacademy.screens.UnitPage

import org.reynem.binomacademy.widgets.AppHeader
import org.reynem.binomacademy.widgets.MainBody
import org.reynem.binomacademy.widgets.SideNavBar
import java.io.File

@Composable
@Preview
fun App() {
    val topics = TopicRepository(File("topics.json"))
    val selectedLesson = remember { mutableStateOf<Lesson?>(null)}
    val selectedIndex = remember { mutableStateOf(0)}
    val currentPage = remember { mutableStateOf(AppScreens.MAIN_PAGE) }
    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            AppHeader()
            when(currentPage.value){
                AppScreens.MAIN_PAGE -> {
                    Row {
                        SideNavBar()
                        MainBody(topics)
                    }
                }
                AppScreens.UNIT_PAGE -> {
                    Row {
                        UnitNavBar(
                            selectedLesson.value!!,
                            onSelect = { selectedIndex.value = it}
                        )
                        UnitPage(selectedLesson.value!!, selectedIndex.value)
                    }
                }
            }
        }
    }
}