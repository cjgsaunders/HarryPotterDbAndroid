package com.example.harrypotterapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("CHARACTER")
data class CharacterEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val actor: String,
    val dateOfBirth: String?,
    val image: String,
    val alive: Boolean,
    val species: String,
    val house: String
)
