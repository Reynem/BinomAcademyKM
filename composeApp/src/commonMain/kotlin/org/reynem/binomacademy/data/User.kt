package org.reynem.binomacademy.data

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var name: String,
    var completedUnitsTotal: Int = 0,
    var completedLessons: List<String> = listOf(),
    var achievements: List<String> = listOf()
) {
    fun changeUserName(name: String) {
        this.name = name
    }
}