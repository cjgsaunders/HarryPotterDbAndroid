package com.example.harrypotterapp.data.mappers

import android.util.Log
import androidx.compose.ui.graphics.Color

fun String.mapHouseToColor(): HouseColor = when (this) {
    "Gryffindor" -> HouseColor.GRYFFINDOR
    "Slytherin" -> HouseColor.SLYTHERIN
    "Hufflepuff" -> HouseColor.HUFFLEPUFF
    "Ravenclaw" -> HouseColor.RAVENCLAW
    else -> {
        Log.i("mapHouseToColor", "not in a house: $this")
        HouseColor.DEFAULT
    }
}

enum class HouseColor(
    val hexColor: Color?
) {
    GRYFFINDOR(Color(0xFF740001)),
    SLYTHERIN(Color(0xFF1A472A)),
    HUFFLEPUFF(Color(0xFFEEB939)),
    RAVENCLAW(Color(0xFF0C1A40)),
    DEFAULT(null)
}
