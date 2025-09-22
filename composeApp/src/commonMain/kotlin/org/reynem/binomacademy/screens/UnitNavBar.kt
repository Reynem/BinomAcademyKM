package org.reynem.binomacademy.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


// TODO: MAKE NAV BAR
@Composable
fun UnitNavBar() {
    Card (
        modifier = Modifier
            .padding(vertical = 12.dp, horizontal = 8.dp)
            .fillMaxHeight()
            .fillMaxWidth(fraction = 0.15f),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = MaterialTheme.colorScheme.onSurface
        ),
    ){
        Column {
            Text("BANNER")
        }
    }
}