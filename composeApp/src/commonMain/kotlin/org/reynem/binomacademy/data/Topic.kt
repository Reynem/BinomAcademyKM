package org.reynem.binomacademy.data

import kotlinx.serialization.Serializable

@Serializable
data class Topic(
    val id: Int,
    val title: String,
    val lessons: List<Lesson>
) {
}