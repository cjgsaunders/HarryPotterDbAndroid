package com.example.harrypotterapp.data

import kotlinx.serialization.Serializable

@Serializable
data class CharacterDto (
    val id: String,
    val name: String
)