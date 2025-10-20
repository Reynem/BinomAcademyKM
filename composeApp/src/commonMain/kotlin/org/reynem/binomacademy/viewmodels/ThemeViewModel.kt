package org.reynem.binomacademy.viewmodels

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import org.reynem.binomacademy.file_manager.ProfileManager
import kotlin.reflect.KClass

val LocalThemeViewModel = compositionLocalOf<ThemeViewModel> {
    error("LocalThemeViewModel not provided")
}

class ThemeViewModel(private val profileManager: ProfileManager) : ViewModel() {
    var darkTheme by mutableStateOf(profileManager.loadUser().darkTheme)

    fun toggleTheme() {
        val newTheme = !darkTheme
        darkTheme = newTheme
        profileManager.updateUser { copy(darkTheme=newTheme) }
    }
}

class ThemeModelFactory(private val profileManager: ProfileManager) :
    ViewModelProvider.NewInstanceFactory()

{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T =
        ThemeViewModel(profileManager) as T
}