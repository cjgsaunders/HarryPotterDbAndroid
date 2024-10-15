package com.example.harrypotterapp.presentation.previewproviders

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.harrypotterapp.domain.models.CharacterModel

class CharacterModelPreviewProvider(): PreviewParameterProvider<List<CharacterModel>> {


        override val values: Sequence<List<CharacterModel>>
            get() = sequenceOf(
                listOf(
                    CharacterModel(
                        name = "Harry Potter",
                        id = "52",
                        actor = "Daniel Radcliffe",
                        houseName = "Griffindor",
                        alive = true,
                        image = "",
                        dateOfBirth = "31-07-1980",
                        houseColour = Color(0xFF740001),
                        species = "human"

                    ),
                    CharacterModel(
                        name = "Cedric Diggory",
                        id = "52",
                        actor = "Robert Pattinson",
                        houseName = "Hufflepuff",
                        alive = true,
                        image = "",
                        dateOfBirth = "31-07-1980",
                        houseColour = Color(0xFFeeb939),
                        species = "human"

                    ),
                    CharacterModel(
                        name = "Cho Chang",
                        id = "52",
                        actor = "Katie Leung",
                        houseName = "Ravenclaw",
                        alive = true,
                        image = "",
                        dateOfBirth = "31-07-1980",
                        houseColour = Color(0xFF0c1a40),
                        species = "human"
                    ),
                    CharacterModel(
                        name = "Severus Snape",
                        id = "52",
                        actor = "Katie Leung",
                        houseName = "Ravenclaw",
                        alive = true,
                        image = "",
                        dateOfBirth = "31-07-1980",
                        houseColour = Color(0xFF1a472a),
                        species = "human"
                    ),
                    CharacterModel(
                        name = "Severus Snape",
                        id = "52",
                        actor = "Katie Leung",
                        houseName = "",
                        alive = true,
                        image = "",
                        dateOfBirth = "31-07-1980",
                        houseColour = Color(0x00000000),
                        species = "human"
                    ),
                )
            )
    }
