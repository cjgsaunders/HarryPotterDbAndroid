package com.example.harrypotterapp.data.mappers

import android.util.Log
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun String?.toFormattedDate(): String? {
    if (this == null) {
        Log.e("DateConversion", "Invalid date: null value provided")
        return null
    }

    return try {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        val date = LocalDate.parse(this, inputFormatter)

        date.format(outputFormatter)
    } catch (e: DateTimeParseException) {
        Log.e("DateConversion", "Invalid date: ${e.message}")
        null
    }
}