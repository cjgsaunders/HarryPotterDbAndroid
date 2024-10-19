package com.example.harrypotterapp.presentation.previewproviders

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.harrypotterapp.domain.models.CharacterModel

class CharacterModelPreviewProvider : PreviewParameterProvider<List<CharacterModel>> {
    override val values: Sequence<List<CharacterModel>>
        get() =
            sequenceOf(
                listOf(
                    CharacterModel(
                        characterName = "Harry Potter",
                        id = "52",
                        actor = "Daniel Radcliffe",
                        houseNameLabel = "Griffindor",
                        alive = true,
                        image = "",
                        dateOfBirth = "31-07-1980",
                        houseColour = Color(0xFF740001),
                        species = "human",
                    ),
                    CharacterModel(
                        characterName = "Cedric Diggory",
                        id = "52",
                        actor = "Robert Pattinson",
                        houseNameLabel = "Hufflepuff",
                        alive = true,
                        image = "",
                        dateOfBirth = "31-07-1980",
                        houseColour = Color(0xFFeeb939),
                        species = "human",
                    ),
                    CharacterModel(
                        characterName = "Cho Chang",
                        id = "52",
                        actor = "Katie Leung",
                        houseNameLabel = "Ravenclaw",
                        alive = true,
                        image = "",
                        dateOfBirth = "31-07-1980",
                        houseColour = Color(0xFF0c1a40),
                        species = "human",
                    ),
                    CharacterModel(
                        characterName = "Severus Snape",
                        id = "52",
                        actor = "Katie Leung",
                        houseNameLabel = "Ravenclaw",
                        alive = true,
                        image = "",
                        dateOfBirth = "31-07-1980",
                        houseColour = Color(0xFF1a472a),
                        species = "human",
                    ),
                    CharacterModel(
                        characterName = "Severus Snape",
                        id = "52",
                        actor = "Katie Leung",
                        houseNameLabel = "",
                        alive = true,
                        image = "",
                        dateOfBirth = "31-07-1980",
                        houseColour = Color(0x00000000),
                        species = "human",
                    ),
                ),
            )
}
