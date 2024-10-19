package com.example.harrypotterapp.data.mappers

import androidx.compose.ui.graphics.Color

fun mapHouseToColor(house: String): HouseColor =
    when (house) {
        "Gryffindor" -> HouseColor.GRYFFINDOR
        "Slytherin" -> HouseColor.SLYTHERIN
        "Hufflepuff" -> HouseColor.HUFFLEPUFF
        "Ravenclaw" -> HouseColor.RAVENCLAW
        else -> HouseColor.DEFAULT
    }

enum class HouseColor(
    val hexColor: Color?,
) {
    GRYFFINDOR(Color(0xFF740001)),
    SLYTHERIN(Color(0xFF1a472a)),
    HUFFLEPUFF(Color(0xFFeeb939)),
    RAVENCLAW(Color(0xFF0c1a40)),
    DEFAULT(null),
}

enum class HouseString(
    val houseString: String,
) {
    GRYFFINDOR("A member of house Gryffindor"),
    SLYTHERIN("A member of house Gryffindor"),
    HUFFLEPUFF("A member of house Gryffindor"),
    RAVENCLAW("A member of house Gryffindor"),
    DEFAULT("Not in a Hogwarts house."),
}

fun Color?.toHexString(): String? =
    this?.let {
        String.format(
            "#%02X%02X%02X%02X",
            (it.alpha * 255).toInt(),
            (it.red * 255).toInt(),
            (it.green * 255).toInt(),
            (it.blue * 255).toInt(),
        )
    }
