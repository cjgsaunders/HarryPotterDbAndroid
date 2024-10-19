package com.example.harrypotterapp.presentation.detailScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.harrypotterapp.domain.models.CharacterModel

@Composable
fun DetailScreenContent( character: CharacterModel) {
    Column() {
        Text(character.characterName)
        Text(character.actor)
        Text(character.species)
        Text(character.houseNameLabel)
        Text(character.dateOfBirth ?: "")
        Text(character.alive.toString())
    }


}