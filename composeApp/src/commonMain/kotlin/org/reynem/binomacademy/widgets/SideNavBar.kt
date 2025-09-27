package org.reynem.binomacademy.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SideNavBar(){
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
        Column (
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ){
            Text(
                text = "My stuff",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 8.dp)
            )

            Button(
                onClick = {},
                shape = RoundedCornerShape(25),
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) {
                Text("Courses")
            }

            Text(
                text = "My account",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 8.dp)
            )

            Button(
                onClick = {},
                shape = RoundedCornerShape(25),
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) {
                Text("Progress")
            }
            Button(
                onClick = {},
                shape = RoundedCornerShape(25),
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) {
                Text("Profile")
            }
        }
    }
}