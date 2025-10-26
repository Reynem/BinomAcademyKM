package org.reynem.binomacademy.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import binomacademy.composeapp.generated.resources.Res
import binomacademy.composeapp.generated.resources.dark_mode
import binomacademy.composeapp.generated.resources.light_mode
import org.jetbrains.compose.resources.painterResource
import org.reynem.binomacademy.file_manager.LocalProfileManager
import org.reynem.binomacademy.viewmodels.LocalAssignmentViewModel

@Composable
fun AppHeader(
    darkTheme: Boolean,
    totalAssignments: Int,
    onChangeTheme: () -> Unit
){
    val profileManager = LocalProfileManager.current
    val assignmentViewModel = LocalAssignmentViewModel.current
    var isToggled by rememberSaveable { mutableStateOf(darkTheme) }
    val profileName = profileManager.user.value.name
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .padding(all = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = profileName)

            Row {
                IconButton(
                    onClick = {onChangeTheme(); isToggled = !isToggled},
                    modifier = Modifier.padding(end = 18.dp).size(24.dp)
                ) {
                    Icon(
                        painter = if(isToggled) painterResource(Res.drawable.dark_mode)
                        else painterResource(Res.drawable.light_mode),
                        contentDescription = "Theme"
                    )
                }

                LinearProgressIndicator(
                    modifier = Modifier.padding(top = 8.dp),
                    progress = { assignmentViewModel.showCurrentProgress(totalAssignments)}
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MaterialTheme.colorScheme.outline )
        )
    }
}