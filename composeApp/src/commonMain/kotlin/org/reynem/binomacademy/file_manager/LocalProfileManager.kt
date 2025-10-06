package org.reynem.binomacademy.file_manager

import androidx.compose.runtime.compositionLocalOf

val LocalProfileManager = compositionLocalOf<ProfileManager> {
    error("LocalProfileManager not provided")
}