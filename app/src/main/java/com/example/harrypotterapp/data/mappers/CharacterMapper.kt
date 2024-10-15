package com.example.harrypotterapp.data.mappers

import com.example.harrypotterapp.data.CharacterDto
import com.example.harrypotterapp.domain.models.CharacterModel

fun CharacterDto.toCharacterModel() : CharacterModel {
    return CharacterModel(
        id = id,
        dateOfBirth = dateOfBirth,
        alive = alive,
        name = name,
        actor = actor,
        species = species,
        houseName = house,
        houseColour = mapHouseToColor(house).hexColor,
        image = image
    )
}

fun  List<CharacterDto>.toCharacterModelList(): List<CharacterModel> {
    val transform: (CharacterDto) -> CharacterModel = {it.toCharacterModel()}
    return this.map(transform)
}