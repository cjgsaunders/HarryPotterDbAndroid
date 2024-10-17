package com.example.harrypotterapp.data.mappers

import androidx.compose.ui.graphics.Color
import com.example.harrypotterapp.data.CharacterDto
import com.example.harrypotterapp.data.database.CharacterEntity
import com.example.harrypotterapp.domain.models.CharacterModel

fun CharacterDto.toCharacterModel() : CharacterModel {
    return CharacterModel(
        id = id,
        dateOfBirth = dateOfBirth,
        alive = alive,
        characterName = name,
        actor = actor.ifEmpty { "Unknown" },
        species = species.ifEmpty { "Unknown" }.replaceFirstChar { it.uppercase() },
        houseNameLabel = house.ifEmpty { "Not in a Hogwarts House" },
        houseColour = mapHouseToColor(house).hexColor,
        image = image
    )
}

fun  List<CharacterDto>.toCharacterModelList(): List<CharacterModel> {
    val transform: (CharacterDto) -> CharacterModel = {it.toCharacterModel()}
    return this.map(transform)
}
