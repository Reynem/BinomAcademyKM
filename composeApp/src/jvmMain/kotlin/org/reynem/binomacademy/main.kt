package org.reynem.binomacademy

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.awt.Dimension

fun main() = application {
    val state = rememberWindowState(placement = WindowPlacement.Maximized)
    Window(
        state = state,
        onCloseRequest = ::exitApplication,
        title = "BinomAcademy",
    ) {
        window.minimumSize = Dimension(1280, 800)
        App()
    }
}