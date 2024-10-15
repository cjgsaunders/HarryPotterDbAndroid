package com.example.harrypotterapp.data

import kotlinx.serialization.Serializable

@Serializable
data class CharacterDto (
    val id: String,
    val name: String,
    val actor: String,
    val house: String,
    val dateOfBirth: String?,
    val image: String,
    val alive: Boolean,
    val species: String
)