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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import org.reynem.binomacademy.data.Assignment


@Composable
fun MultipleChoiceView(assignment: Assignment.MultipleChoice, onCompleted: (String) -> Unit) {
    val radioOptions = assignment.options
    val (selectedOption, onOptionSelected) = remember(assignment.id)
    { mutableStateOf(radioOptions[0]) }

    if (selectedOption == assignment.correctAnswer) {
        onCompleted(assignment.id)
    }

    Column(modifier = Modifier.selectableGroup()) {
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = null // null recommended for accessibility with screen readers
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@Composable
fun TextInputView(assignment: Assignment.TextInput, onCompleted: (String) -> Unit) {
    var text by remember(assignment.id) { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it},
        modifier = Modifier.background(MaterialTheme.colorScheme.surface)
    )

    if (text == assignment.correctAnswer) {
        onCompleted(assignment.id)
    }
}

@Composable
fun TrueFalseView(assignment: Assignment.TrueFalse, onCompleted: (String) -> Unit) {
    val radioOptions = listOf(true, false)
    val (selectedOption, onOptionSelected) = remember(assignment.id)
    { mutableStateOf(radioOptions[0]) }

    if (selectedOption == assignment.correctAnswer) {
        onCompleted(assignment.id)
    }

    Column(modifier = Modifier.selectableGroup()) {
        radioOptions.forEach { boolOption ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (boolOption == selectedOption),
                        onClick = { onOptionSelected(boolOption) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (boolOption == selectedOption),
                    onClick = null // null recommended for accessibility with screen readers
                )
                Text(
                    text = boolOption.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@Composable
fun NumberInputView(assignment: Assignment.NumberInput, onCompleted: (String) -> Unit) {
    var text by remember(assignment.id) { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it},
        modifier = Modifier.background(MaterialTheme.colorScheme.surface)
    )

    if (text == assignment.correctAnswer.toString()) {
        onCompleted(assignment.id)
    }
}