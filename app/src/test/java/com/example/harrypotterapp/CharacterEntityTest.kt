package com.example.harrypotterapp

import android.util.Log
import com.example.harrypotterapp.data.database.CharacterEntity
import com.example.harrypotterapp.domain.mappers.HouseColor
import com.example.harrypotterapp.domain.mappers.mapHouseToColor
import com.example.harrypotterapp.domain.mappers.toCharacterModel
import com.example.harrypotterapp.domain.mappers.toCharacterModelList
import com.example.harrypotterapp.domain.mappers.toFormattedDate
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class CharacterEntityTest {

    private val mockedDate = "29 Feb 2023"
    private val mockedHouseColor = HouseColor.GRYFFINDOR
    private val characterEntityGoodData = CharacterEntity(
        id = "1",
        dateOfBirth = "1-1-2023",
        alive = true,
        name = "Harry Potter",
        actor = "Daniel Radcliffe",
        species = "human",
        house = "Gryffindor",
        image = "image_url"
    )
    private val characterEntityEmptyData = CharacterEntity(
        id = "2",
        dateOfBirth = "1-1-2023",
        alive = false,
        name = "Hermione Granger",
        actor = "",
        species = "",
        house = "",
        image = "image_url"
    )

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        mockkStatic(String::toFormattedDate)
        mockkStatic(String::mapHouseToColor)
        every { any<String>().toFormattedDate() } returns mockedDate
        every { "Gryffindor".mapHouseToColor() } returns mockedHouseColor
        every { "".mapHouseToColor() } returns HouseColor.DEFAULT
        every { null.toFormattedDate() } returns null
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `given an entity with good data, when mapped to model, return model with good data`() {
        val characterModel = characterEntityGoodData.toCharacterModel()

        assertNotNull(characterModel)
        assertEquals("1", characterModel.id)
        assertEquals(mockedDate, characterModel.dateOfBirth)
        assertEquals(true, characterModel.alive)
        assertEquals("Harry Potter", characterModel.characterName)
        assertEquals("Daniel Radcliffe", characterModel.actor)
        assertEquals("Human", characterModel.species)
        assertEquals("Gryffindor", characterModel.houseNameLabel)
        assertEquals(mockedHouseColor.hexColor, characterModel.houseColour)
        assertEquals("image_url", characterModel.image)
    }

    @Test
    fun `given an entity with missing fields, when mapped to model, return model with correct placeholders`() {
        val characterModel = characterEntityEmptyData.toCharacterModel()

        assertNotNull(characterModel)
        assertEquals("2", characterModel.id)
        assertEquals(mockedDate, characterModel.dateOfBirth)
        assertEquals(false, characterModel.alive)
        assertEquals("Hermione Granger", characterModel.characterName)
        assertEquals("Unknown", characterModel.actor)
        assertEquals("Unknown", characterModel.species)
        assertEquals("Not in a Hogwarts House", characterModel.houseNameLabel)
        assertEquals(null, characterModel.houseColour)
        assertEquals("image_url", characterModel.image)
    }

    @Test
    fun `given a list of two entities, when mapped to a list of models, return a list of two models`() {
        val characterEntities = listOf(
            characterEntityEmptyData,
            characterEntityEmptyData
        )

        val characterModels = characterEntities.toCharacterModelList()

        assertEquals(2, characterModels.size)
        assertEquals(mockedDate, characterModels[0].dateOfBirth)
        assertEquals(null, characterModels[0].houseColour)
        assertEquals(mockedDate, characterModels[1].dateOfBirth)
        assertEquals(null, characterModels[1].houseColour)
    }

    @Test
    fun `given a list of one entities, when mapped to a list of models, return a list of one models`() {
        val characterEntities = listOf(
            characterEntityEmptyData
        )

        val characterModels = characterEntities.toCharacterModelList()

        assertEquals(1, characterModels.size)
        assertEquals(mockedDate, characterModels[0].dateOfBirth)
        assertEquals(null, characterModels[0].houseColour)
    }

    @Test
    fun `given an empty list of entities, when mapped to a list of models, return a list of empty models`() {
        val characterEntities = emptyList<CharacterEntity>()

        val characterModels = characterEntities.toCharacterModelList()

        assertEquals(0, characterModels.size)
    }
}
