package org.reynem.binomacademy.file_manager

expect class PlatformFileStorage() {
    fun getUserDataPath(): String
    fun writeFile(path: String, content: String)
    fun readFile(path: String): String?
}