package org.reynem.binomacademy.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

val numberFormat by lazy {
    DecimalFormat("0.##").apply {
        applyPattern("0.00")
        decimalFormatSymbols = DecimalFormatSymbols(Locale.US)
    }
}

fun normalizeNumberInput(input: String): String? {
    return try {
        val number = input.replace(",", ".").toDouble()
        numberFormat.format(number)
    } catch (_: NumberFormatException) {
        null
    }
}