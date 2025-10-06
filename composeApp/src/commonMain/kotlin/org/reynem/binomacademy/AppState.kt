package org.reynem.binomacademy

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import org.reynem.binomacademy.data.Lesson
import org.reynem.binomacademy.screens.AppScreens

val LocalAppState = staticCompositionLocalOf<AppState> {
    error("AppState not provided")
}

class AppState {
    var currentPage by mutableStateOf(AppScreens.MAIN_PAGE)
    var selectedLesson by mutableStateOf<Lesson?>(null)
    var selectedUnitIndex by mutableStateOf(0)

    fun navigateToLesson(lesson: Lesson) {
        selectedLesson = lesson
        selectedUnitIndex = 0
        currentPage = AppScreens.UNIT_PAGE
    }

    fun selectUnit(index: Int) {
        selectedUnitIndex = index
    }

    fun backToMain() {
        currentPage = AppScreens.MAIN_PAGE
        selectedLesson = null
        selectedUnitIndex = 0
    }

    fun changePage(page: AppScreens) {
        currentPage = page
    }
}

