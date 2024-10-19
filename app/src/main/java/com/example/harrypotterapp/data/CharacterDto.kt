package com.example.harrypotterapp.data

import com.example.harrypotterapp.data.database.CharacterEntity
import kotlinx.serialization.Serializable

@Serializable
data class CharacterDto(
    val id: String,
    val name: String,
    val actor: String,
    val house: String,
    val dateOfBirth: String?,
    val image: String,
    val alive: Boolean,
    val species: String
)

fun CharacterDto.toDb() = CharacterEntity(
    id = id,
    name = name,
    actor = actor,
    alive = alive,
    house = house,
    dateOfBirth = dateOfBirth,
    image = image,
    species = species
)
