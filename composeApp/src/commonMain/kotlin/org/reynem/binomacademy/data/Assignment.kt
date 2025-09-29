package org.reynem.binomacademy.data

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable(with = AssignmentSerializer::class)
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

    fun isAnswerCorrect(userAnswer: Any): Boolean {
        return when (this) {
            is MultipleChoice -> userAnswer == correctAnswer
            is TextInput -> userAnswer.toString().trim().equals(correctAnswer, ignoreCase = true)
            is TrueFalse -> userAnswer == correctAnswer
            is NumberInput -> {
                val num = (userAnswer as? Number)?.toDouble() ?: return false
                kotlin.math.abs(num - correctAnswer) < 0.001
            }
        }
    }
}

object AssignmentSerializer : JsonContentPolymorphicSerializer<Assignment>(Assignment::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<Assignment> {
        val type = element.jsonObject["type"]?.jsonPrimitive?.contentOrNull
            ?: throw SerializationException("Missing 'type' field in Assignment JSON")

        return when (type) {
            "multiple_choice" -> Assignment.MultipleChoice.serializer()
            "text_input" -> Assignment.TextInput.serializer()
            "true_false" -> Assignment.TrueFalse.serializer()
            "number_input" -> Assignment.NumberInput.serializer()
            else -> throw SerializationException("Unknown assignment type: $type")
        }
    }
}