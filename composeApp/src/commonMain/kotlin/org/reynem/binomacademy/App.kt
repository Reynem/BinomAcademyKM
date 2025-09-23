package org.reynem.binomacademy

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.reynem.binomacademy.data.TopicRepository
import org.reynem.binomacademy.screens.AppScreens
import org.reynem.binomacademy.screens.UnitNavBar
import org.reynem.binomacademy.screens.UnitPage
import org.reynem.binomacademy.theme.AppTheme

import org.reynem.binomacademy.widgets.AppHeader
import org.reynem.binomacademy.widgets.MainBody
import org.reynem.binomacademy.widgets.SideNavBar
import java.io.File

@Composable
@Preview
fun App() {
    val topics = TopicRepository(File("topics.json"))
    val appState = remember { AppState() }

    CompositionLocalProvider(LocalAppState provides appState) {
        AppTheme (
            darkTheme = false
        ){
            Column(Modifier.fillMaxSize()) {
                AppHeader()
                when (appState.currentPage) {
                    AppScreens.MAIN_PAGE -> {
                        Row {
                            SideNavBar()
                            MainBody(topics)
                        }
                    }
                    AppScreens.UNIT_PAGE -> {
                        Row {
                            UnitNavBar(
                                topics,
                                appState.selectedLesson!!,
                                onSelect = { appState.selectUnit(it) }
                            )
                            UnitPage(appState.selectedLesson!!, appState.selectedUnitIndex)
                        }
                    }
                }
            }
        }
    }
}
