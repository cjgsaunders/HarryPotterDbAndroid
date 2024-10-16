package com.example.harrypotterapp.domain.models

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.harrypotterapp.domain.Searchable

@Entity("CHARACTER")
data class CharacterModel(
    @PrimaryKey
    val id: String,
    val characterName: String,
    val actor: String,
    val houseColour: Color,
    val dateOfBirth: String?,
    val image: String,
    val alive: Boolean,
    val species: String,
    val houseNameLabel: String
) : Searchable {
    override fun searchCharacterInfo(searchString: String): Boolean {
        return listOf(
            characterName, actor, species, houseNameLabel
        ).any { it.contains(searchString, ignoreCase = true) }
    }
}