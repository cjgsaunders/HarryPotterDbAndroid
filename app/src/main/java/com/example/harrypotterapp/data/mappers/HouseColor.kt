package com.example.harrypotterapp.data.mappers

import android.util.Log
import androidx.compose.ui.graphics.Color

fun mapHouseToColor(house: String): HouseColor = when (house) {
    "Gryffindor" -> HouseColor.GRYFFINDOR
    "Slytherin" -> HouseColor.SLYTHERIN
    "Hufflepuff" -> HouseColor.HUFFLEPUFF
    "Ravenclaw" -> HouseColor.RAVENCLAW
    else -> {
        Log.i("mapHouseToColor", "not in a house: $house")
        HouseColor.DEFAULT
    }
}

enum class HouseColor(
    val hexColor: Color?
) {
    GRYFFINDOR(Color(0xFF740001)),
    SLYTHERIN(Color(0xFF1a472a)),
    HUFFLEPUFF(Color(0xFFeeb939)),
    RAVENCLAW(Color(0xFF0c1a40)),
    DEFAULT(null)
}
