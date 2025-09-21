package org.reynem.binomacademy.data

import kotlinx.serialization.Serializable

@Serializable
data class Lesson(
    val topicId: Int,
    val id: Int,
    val title: String,
    val units: List<UnitData>
) {

}
