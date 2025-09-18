package org.reynem.binomacademy.data

data class Topic(
    val id: Int,
    val title: String,
    val lessons: List<Lesson>
) {
}