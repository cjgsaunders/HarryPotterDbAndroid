package com.example.harrypotterapp.presentation.previewproviders

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.harrypotterapp.domain.CharacterDomainModel

class CharacterModelPreviewProvider : PreviewParameterProvider<List<CharacterDomainModel>> {
    override val values: Sequence<List<CharacterDomainModel>>
        get() =
            sequenceOf(
                listOf(
                    CharacterDomainModel(
                        characterName = "Harry Potter",
                        id = "52",
                        actor = "Daniel Radcliffe",
                        houseNameLabel = "Griffindor",
                        alive = true,
                        image = "",
                        dateOfBirth = "31-07-1980",
                        houseColour = Color(0xFF740001),
                        species = "human"
                    ),
                    CharacterDomainModel(
                        characterName = "Cedric Diggory",
                        id = "52",
                        actor = "Robert Pattinson",
                        houseNameLabel = "Hufflepuff",
                        alive = true,
                        image = "",
                        dateOfBirth = "31-07-1980",
                        houseColour = Color(0xFFeeb939),
                        species = "human"
                    ),
                    CharacterDomainModel(
                        characterName = "Cho Chang",
                        id = "52",
                        actor = "Katie Leung",
                        houseNameLabel = "Ravenclaw",
                        alive = true,
                        image = "",
                        dateOfBirth = "31-07-1980",
                        houseColour = Color(0xFF0c1a40),
                        species = "human"
                    ),
                    CharacterDomainModel(
                        characterName = "Severus Snape",
                        id = "52",
                        actor = "Katie Leung",
                        houseNameLabel = "Ravenclaw",
                        alive = true,
                        image = "",
                        dateOfBirth = "31-07-1980",
                        houseColour = Color(0xFF1a472a),
                        species = "human"
                    ),
                    CharacterDomainModel(
                        characterName = "Severus Snape",
                        id = "52",
                        actor = "Katie Leung",
                        houseNameLabel = "",
                        alive = true,
                        image = "",
                        dateOfBirth = "31-07-1980",
                        houseColour = Color(0x00000000),
                        species = "human"
                    )
                )
            )
}
