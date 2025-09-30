package org.reynem.binomacademy.widgets

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.reynem.binomacademy.data.Assignment


@Composable
fun MultipleChoiceView(assignment: Assignment.MultipleChoice) {
    assignment.options.forEach {
        Text(it)
    }
}

@Composable
fun TextInputView(assignment: Assignment.TextInput) {
    Text(assignment.title)
}

@Composable
fun TrueFalseView(assignment: Assignment.TrueFalse) {
    Text(assignment.title)
}

@Composable
fun NumberInputView(assignment: Assignment.NumberInput) {
    Text(assignment.title)
}