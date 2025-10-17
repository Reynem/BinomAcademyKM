package org.reynem.binomacademy.data

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var name: String,
    var avatarPath: String = "",
    var completedUnitsTotal: Int = 0,
    var completedAssignmentsTotal: Int = 0,
    var completedAssignments: Set<String> = setOf(),
    var completedUnits: Set<String> = setOf(),
    var achievements: List<String> = listOf(),
    var darkTheme: Boolean = false
) {
    fun changeUserName(name: String) {
        this.name = name
    }
}