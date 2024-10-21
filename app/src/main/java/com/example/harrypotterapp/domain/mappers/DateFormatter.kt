package com.example.harrypotterapp.domain.mappers

import android.util.Log
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.util.Locale

fun String?.toFormattedDate(): String? {
    val outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    val formatter = DateTimeFormatterBuilder()
        .appendOptional(DateTimeFormatter.ofPattern("d-M-yyyy"))
        .appendOptional(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        .toFormatter(Locale.getDefault())

    return try {
        LocalDate.parse(this, formatter).format(outputFormatter).also {
            Log.i("DateConversion", "success $it")
        }
    } catch (e: Exception) {
        Log.e("DateConversion", "Invalid date: ${e.message} $this")
        null
    }
}
