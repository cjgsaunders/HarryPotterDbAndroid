package com.example.harrypotterapp.data.database

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.harrypotterapp.domain.models.CharacterModel

@Entity("CHARACTER")
data class CharacterEntity(
    @PrimaryKey
    val id: String,
    val characterName: String,
    val actor: String,
    val houseColour: String?,
    val dateOfBirth: String?,
    val image: String,
    val alive: Boolean,
    val species: String,
    val houseNameLabel: String,
)

fun CharacterEntity.toDomainModel() =
    CharacterModel(
        id = id,
        characterName = characterName,
        actor = actor,
        alive = alive,
        houseNameLabel = houseNameLabel,
        dateOfBirth = dateOfBirth,
        image = image,
        species = species,
        houseColour = houseColour?.let { Color(android.graphics.Color.parseColor(houseColour)) },
    )
