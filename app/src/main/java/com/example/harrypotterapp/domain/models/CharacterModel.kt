package com.example.harrypotterapp.domain.models

import androidx.compose.ui.graphics.Color
import com.example.harrypotterapp.data.database.CharacterEntity
import com.example.harrypotterapp.data.mappers.toHexString
import com.example.harrypotterapp.domain.Searchable

data class CharacterModel(
    val id: String,
    val characterName: String,
    val actor: String,
    val houseColour: Color?,
    val dateOfBirth: String?,
    val image: String,
    val alive: Boolean,
    val species: String,
    val houseNameLabel: String,
) : Searchable {
    override fun searchCharacterInfo(searchString: String): Boolean =
        listOf(
            characterName,
            actor,
            species,
            houseNameLabel,
        ).any { it.contains(searchString, ignoreCase = true) }
}

fun CharacterModel.toDb() =
    CharacterEntity(
        id = id,
        characterName = characterName,
        actor = actor,
        alive = alive,
        houseNameLabel = houseNameLabel,
        dateOfBirth = dateOfBirth,
        image = image,
        species = species,
        houseColour = houseColour.toHexString(),
    )
