package org.reynem.binomacademy.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import org.reynem.binomacademy.file_manager.ProfileManager
import kotlin.reflect.KClass

class ThemeViewModel(private val profileManager: ProfileManager) : ViewModel() {
    var darkTheme by mutableStateOf(profileManager.loadUser().darkTheme)

    fun toggleTheme() {
        darkTheme = !darkTheme
        profileManager.updateUser { copy(darkTheme=darkTheme) }
    }
}

class ThemeModelFactory(private val profileManager: ProfileManager) :
    ViewModelProvider.NewInstanceFactory()

{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T =
        ThemeViewModel(profileManager) as T
}