package org.reynem.binomacademy.data

data class User(
    var name: String,
    var completedUnitsTotal: Int,
    var completedLessons: List<String>,
    var achievements: List<String>
) {
    fun changeUserName(name: String) {
        this.name = name
    }
}