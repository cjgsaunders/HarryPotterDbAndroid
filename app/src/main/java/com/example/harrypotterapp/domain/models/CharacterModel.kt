package com.example.harrypotterapp.domain.models

import androidx.compose.ui.graphics.Color

data class CharacterModel(
    val id: String,
    val name: String,
    val actor: String,
    val houseColour: Color,
    val dateOfBirth: String?,
    val image: String,
    val alive: Boolean,
    val species: String,
    val houseName: String
)