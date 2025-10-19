package org.reynem.binomacademy

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.reynem.binomacademy.repositories.TopicRepository
import org.reynem.binomacademy.file_manager.LocalProfileManager
import org.reynem.binomacademy.file_manager.ProfileManager
import org.reynem.binomacademy.screens.AppScreens
import org.reynem.binomacademy.widgets.UnitNavBar
import org.reynem.binomacademy.screens.UnitPage
import org.reynem.binomacademy.theme.AppTheme

import org.reynem.binomacademy.widgets.AppHeader
import org.reynem.binomacademy.screens.MainBody
import org.reynem.binomacademy.screens.ProfilePage
import org.reynem.binomacademy.viewmodels.AssignmentModelFactory
import org.reynem.binomacademy.viewmodels.AssignmentViewModel
import org.reynem.binomacademy.viewmodels.LocalAssignmentViewModel
import org.reynem.binomacademy.viewmodels.ThemeModelFactory
import org.reynem.binomacademy.viewmodels.ThemeViewModel
import org.reynem.binomacademy.viewmodels.TopicIndex
import org.reynem.binomacademy.widgets.SideNavBar
import java.io.File
import kotlin.apply

@Composable
@Preview
fun App() {
    val topics = TopicRepository(File("topics.json"))
    val appState = remember { AppState() }
    val profileManager = remember { ProfileManager().apply { initialize() }}
    val themeViewModel: ThemeViewModel = viewModel(factory = ThemeModelFactory(profileManager))
    val topicIndex = TopicIndex().apply { this.buildIndexes(topics.getAll()) }
    val assignmentViewModel: AssignmentViewModel = viewModel(factory = AssignmentModelFactory(profileManager, topicIndex))

    CompositionLocalProvider(
        LocalAppState provides appState,
        LocalProfileManager provides profileManager,
        LocalAssignmentViewModel provides assignmentViewModel
    ) {
        AppTheme (
            darkTheme = themeViewModel.darkTheme
        ){
            Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
                AppHeader(
                    themeViewModel.darkTheme,
                    onChangeTheme = {
                        themeViewModel.toggleTheme()
                    }
                )
                AnimatedContent(
                    targetState = appState.currentPage,
                    label = "Animated Content",
                    transitionSpec = {
                        fadeIn(tween(300)) togetherWith
                                fadeOut(tween(300))
                    }
                ){ targetPage ->
                    when (targetPage) {
                        AppScreens.MAIN_PAGE -> {
                            Row {
                                SideNavBar(
                                    onSelect = { appState.changePage(it) }
                                )
                                MainBody(topics)
                            }
                        }
                        AppScreens.PROFILE_PAGE -> {
                            Row {
                                SideNavBar(
                                    onSelect = { appState.changePage(it) }
                                )
                                ProfilePage()
                            }
                        }
                        AppScreens.UNIT_PAGE -> {
                            val lesson = appState.selectedLesson
                            if (lesson != null) {
                                Row {
                                    UnitNavBar(
                                        topics,
                                        lesson,
                                        onSelect = { appState.selectUnit(it) }
                                    )
                                    UnitPage(lesson, topicIndex = topicIndex, index = appState.selectedUnitIndex)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
