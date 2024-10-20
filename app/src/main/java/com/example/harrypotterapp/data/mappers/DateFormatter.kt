package com.example.harrypotterapp.data.mappers

import android.util.Log
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// fun String?.toFormattedDate(): String? {
//    if (this == null) {
//        Log.e("DateConversion", "Invalid date: null value provided")
//        return null
//    }
//
//    return try {
//        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//        val outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
//
//        val date = LocalDate.parse(this, inputFormatter)
//
//        date.format(outputFormatter)
//    } catch (e: DateTimeParseException) {
//        Log.e("DateConversion", "Invalid date: ${e.message}")
//        null
//    }
// }

// fun String?.toFormattedDate(): String? {
//    return this?.let { dateString ->
//        val patterns = listOf("d-M-yyyy", "dd-MM-yyyy")
//        val outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
//        var result: String? = null
//
//        for (datePattern in patterns) {
//            result = runCatching {
//                val inputFormatter = DateTimeFormatter.ofPattern(datePattern)
//                LocalDate.parse(dateString, inputFormatter)
//            }.getOrNull()?.format(outputFormatter)
//
//            if (result != null) {
//                break
//            }
//        }
//        return result
//    }
// }

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
