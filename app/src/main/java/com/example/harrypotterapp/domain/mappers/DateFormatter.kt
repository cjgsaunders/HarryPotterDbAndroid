package com.example.harrypotterapp.domain.mappers

import android.util.Log
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun String?.toFormattedDate(): String? {
    var result: String? = null
    val patterns = listOf("d-M-yyyy", "dd-MM-yyyy")
    val outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")

    for (datePattern in patterns) {
        val inputFormatter = DateTimeFormatter.ofPattern(datePattern)
        try {
            val date = LocalDate.parse(this, inputFormatter)
            result = date.format(outputFormatter)
            Log.i("DateConversion", "success $result")
            break
        } catch (e: Exception) {
            Log.e("DateConversion", "Invalid date: ${e.message} $this")
        }
    }
    return result
}
