package org.reynem.binomacademy.file_manager

import kotlinx.serialization.json.Json
import org.reynem.binomacademy.data.User
import java.io.File

class ProfileManager {
    val storage = PlatformFileStorage()
    val json = Json
    val profileFilePath = "${storage.getUserDataPath()}\\profile.json"

    fun initialize() {
        if (!File(profileFilePath).exists()) {
            val user = User(
                name = "Hello",
                completedUnitsTotal = 0,
                completedLessons = listOf(),
                achievements = listOf()
            )
            val userJson = json.encodeToString(user)
            storage.writeFile(profileFilePath, userJson)
        }
    }

    fun writeUser(user: User) {
        val profileJson = json.encodeToString(user)
        storage.writeFile(profileFilePath, profileJson)
    }

    fun readUser() : User {
        val storageData = storage.readFile(profileFilePath)
        return if (storageData != null) {
            try {
                json.decodeFromString(storageData)
            } catch (e: Exception) {
                println("Error while reading JSON: $e")
                User(name = "Hello")
            }
        } else {
            User(name = "Hello")
        }
    }

    fun getUserName() : String = readUser().name
}