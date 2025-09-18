package org.reynem.binomacademy

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform