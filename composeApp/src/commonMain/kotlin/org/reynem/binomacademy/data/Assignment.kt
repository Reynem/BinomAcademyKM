package org.reynem.binomacademy.data

import kotlinx.serialization.*


@Serializable
sealed class Assignment {
    abstract val id: String
    abstract val title: String
    abstract val correctAnswer: Any

    @Serializable
    @SerialName("multiple_choice")
    data class MultipleChoice(
        override val id: String,
        override val title: String,
        val options: List<String>,
        override val correctAnswer: String
    ) : Assignment()

    @Serializable
    @SerialName("text_input")
    data class TextInput(
        override val id: String,
        override val title: String,
        override val correctAnswer: String
    ) : Assignment()

    @Serializable
    @SerialName("true_false")
    data class TrueFalse(
        override val id: String,
        override val title: String,
        override val correctAnswer: Boolean
    ) : Assignment()

    @Serializable
    @SerialName("number_input")
    data class NumberInput(
        override val id: String,
        override val title: String,
        override val correctAnswer: Double
    ) : Assignment()
}