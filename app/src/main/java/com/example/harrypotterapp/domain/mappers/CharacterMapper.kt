package com.example.harrypotterapp.domain.mappers

import com.example.harrypotterapp.data.database.CharacterEntity
import com.example.harrypotterapp.domain.CharacterDomainModel

fun CharacterEntity.toCharacterModel(): CharacterDomainModel = CharacterDomainModel(
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

fun List<CharacterEntity>.toCharacterModelList(): List<CharacterDomainModel> {
    val transform: (CharacterEntity) -> CharacterDomainModel = { it.toCharacterModel() }
    return this.map(transform)
}
