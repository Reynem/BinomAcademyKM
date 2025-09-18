package org.reynem.binomacademy.data

data class Lesson(
    val topicId: Int,
    val id: Int,
    val title: String,
    val units: List<Unit>
) {

}
