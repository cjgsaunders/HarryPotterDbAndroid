package com.example.harrypotterapp.domain

import androidx.compose.ui.graphics.Color

data class CharacterDomainModel(
    val id: String,
    val characterName: String,
    val actor: String,
    val houseColour: Color?,
    val dateOfBirth: String?,
    val image: String,
    val alive: Boolean,
    val species: String,
    val houseNameLabel: String
)
