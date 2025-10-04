package org.reynem.binomacademy.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import org.reynem.binomacademy.data.Assignment

@Composable
fun MultipleChoiceView(
    assignment: Assignment.MultipleChoice,
    currentAnswer: String?,
    onAnswerChanged: (String) -> Unit
) {
    Column(modifier = Modifier.selectableGroup()) {
        assignment.options.forEach { option ->
            val isSelected = option == currentAnswer
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = isSelected,
                        onClick = { onAnswerChanged(option) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = isSelected,
                    onClick = null // recommended for accessibility
                )
                Text(
                    text = option,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@Composable
fun TextInputView(
    assignment: Assignment.TextInput,
    currentAnswer: String?,
    onAnswerChanged: (String) -> Unit
) {
    TextField(
        value = currentAnswer ?: "",
        onValueChange = onAnswerChanged,
        modifier = Modifier.background(MaterialTheme.colorScheme.surface)
    )
}

@Composable
fun TrueFalseView(
    assignment: Assignment.TrueFalse,
    currentAnswer: String?,
    onAnswerChanged: (String) -> Unit
) {
    val options = listOf("true" to "True", "false" to "False")

    Column(modifier = Modifier.selectableGroup()) {
        options.forEach { (boolValue, label) ->
            val isSelected = boolValue == currentAnswer
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = isSelected,
                        onClick = { onAnswerChanged(boolValue) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = isSelected,
                    onClick = null
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@Composable
fun NumberInputView(
    assignment: Assignment.NumberInput,
    currentAnswer: String?,
    onAnswerChanged: (String) -> Unit
) {
    TextField(
        value = currentAnswer ?: "",
        onValueChange = onAnswerChanged,
        modifier = Modifier.background(MaterialTheme.colorScheme.surface),
        singleLine = true,
    )
}