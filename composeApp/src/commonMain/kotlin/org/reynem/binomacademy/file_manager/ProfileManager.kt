package org.reynem.binomacademy.file_manager

import java.io.File

class ProfileManager {
    fun initialize() {
        val storage = PlatformFileStorage()
        val profileFilePath = "${storage.getUserDataPath()}\\profile.json"
        if (!File(profileFilePath).exists()) {
            storage.writeFile(profileFilePath, "")
        }
    }
}