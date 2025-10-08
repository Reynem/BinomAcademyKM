package org.reynem.binomacademy.data

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var name: String,
    var avatarPath: String = "",
    var completedUnitsTotal: Int = 0,
    var completedLessons: Set<String> = setOf(),
    var achievements: List<String> = listOf()
) {
    fun changeUserName(name: String) {
        this.name = name
    }
}