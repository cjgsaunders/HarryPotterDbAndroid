package com.example.harrypotterapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity("CHARACTER")
@Serializable
data class CharacterDto (
    @PrimaryKey
    val id: String,
    val name: String,
    val actor: String,
    val house: String,
    val dateOfBirth: String?,
    val image: String,
    val alive: Boolean,
    val species: String
)