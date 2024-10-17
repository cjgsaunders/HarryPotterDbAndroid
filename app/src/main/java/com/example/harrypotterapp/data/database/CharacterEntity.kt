package com.example.harrypotterapp.data.database

import android.graphics.Color as C
import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.harrypotterapp.data.CharacterDto
import com.example.harrypotterapp.domain.models.CharacterModel

@Entity("CHARACTER")
data class CharacterEntity (
    @PrimaryKey
    val id: String,
    val characterName: String,
    val actor: String,
    val houseColour: String,
    val dateOfBirth: String?,
    val image: String,
    val alive: Boolean,
    val species: String,
    val houseNameLabel: String
)

fun Color.toHexString(): String {
    return String.format("#%02X%02X%02X%02X", (alpha * 255).toInt(), (red * 255).toInt(), (green * 255).toInt(), (blue * 255).toInt())
}

internal fun CharacterModel.toDb() = CharacterEntity(
    id = id,
    characterName = characterName,
    actor = actor,
    alive = alive,
    houseNameLabel = houseNameLabel,
    dateOfBirth = dateOfBirth,
    image = image,
    species = species,
    houseColour = houseColour.toHexString()
)

internal fun CharacterEntity.toDomainModel() = CharacterModel(
    id = id,
    characterName = characterName,
    actor = actor,
    alive = alive,
    houseNameLabel = houseNameLabel,
    dateOfBirth = dateOfBirth,
    image = image,
    species = species,
    houseColour = Color(C.parseColor(houseColour))
)
