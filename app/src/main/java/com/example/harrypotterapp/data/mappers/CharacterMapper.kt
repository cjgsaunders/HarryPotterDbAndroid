package com.example.harrypotterapp.data.mappers

import com.example.harrypotterapp.data.database.CharacterEntity
import com.example.harrypotterapp.domain.models.CharacterModel

fun CharacterEntity.toCharacterModel(): CharacterModel = CharacterModel(
    id = id,
    dateOfBirth = dateOfBirth.toFormattedDate(),
    alive = alive,
    characterName = name,
    actor = actor.ifEmpty { "Unknown" },
    species = species.ifEmpty { "Unknown" }.replaceFirstChar { it.uppercase() },
    houseNameLabel = house.ifEmpty { "Not in a Hogwarts House" },
    houseColour = house.mapHouseToColor().hexColor,
    image = image
)

fun List<CharacterEntity>.toCharacterModelList(): List<CharacterModel> {
    val transform: (CharacterEntity) -> CharacterModel = { it.toCharacterModel() }
    return this.map(transform)
}
