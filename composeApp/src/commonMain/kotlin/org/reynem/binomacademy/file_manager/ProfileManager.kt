package org.reynem.binomacademy.file_manager

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import kotlinx.serialization.json.Json
import org.reynem.binomacademy.data.User
import java.io.File

class ProfileManager {
    val storage = PlatformFileStorage()
    val json = Json
    val profileFilePath = "${storage.getUserDataPath()}\\profile.json"

    private val _user = mutableStateOf(loadUser())
    val user: State<User> = _user

    fun initialize() {
        if (!File(profileFilePath).exists()) {
            val user = User(
                name = "Hello",
                completedUnitsTotal = 0,
                completedAssignmentsTotal = 0,
                completedUnits = setOf(),
                completedAssignments = setOf(),
                achievements = listOf()
            )
            val userJson = json.encodeToString(user)
            storage.writeFile(profileFilePath, userJson)
        }
    }

    fun loadUser(): User {
        return try {
            val content = storage.readFile(profileFilePath)
            if (content != null) {
                json.decodeFromString(content)
            } else {
                User(name = "Anonymous", completedUnitsTotal = 0)
            }
        } catch (e: Exception) {
            println("Error while reading a Json: $e")
            User(name = "Anonymous", completedUnitsTotal = 0)
        }
    }

    fun updateUser(update: User.() -> User) {
        val newUser = _user.value.update()
        _user.value = newUser
        storage.writeFile(profileFilePath, json.encodeToString(newUser))
    }
}