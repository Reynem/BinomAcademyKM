package org.reynem.binomacademy.file_manager

import java.io.File

actual class PlatformFileStorage {
    // The function will return %APPDATA% directory for the project
    actual fun getUserDataPath(): String {
        val appData = System.getenv("LOCALAPPDATA")
        val path = "$appData\\BinomAcademy"
        File(path).mkdirs()
        return path
    }

    actual fun writeFile(path: String, content: String) {
        File(path).writeText(content, Charsets.UTF_8)
    }

    actual fun readFile(path: String): String? {
        return if (File(path).exists()) File(path).readText(Charsets.UTF_8) else null
    }
}