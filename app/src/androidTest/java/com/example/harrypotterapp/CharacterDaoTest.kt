package com.example.harrypotterapp

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.harrypotterapp.data.database.CharacterDao
import com.example.harrypotterapp.data.database.CharacterDatabase
import com.example.harrypotterapp.data.database.CharacterEntity
import com.google.common.truth.Truth.assertThat
import java.io.IOException
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CharacterDaoTest {

    private lateinit var dao: CharacterDao
    private lateinit var dataBase: CharacterDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        dataBase = Room.inMemoryDatabaseBuilder(
            context, CharacterDatabase::class.java
        ).build()
        dao = dataBase.dao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        if (::dataBase.isInitialized) {
            dataBase.close()
        }
    }

    @Test
    fun upsertCharacter_insertsCharacter() = runTest {
        val character =
            CharacterEntity(
                id = "1",
                name = "Harry Potter",
                actor = "Daniel Radcliffe",
                dateOfBirth = "",
                "",
                true,
                "",
                ""
            )
        dao.upsertCharacter(character)

        val allCharacters = dao.getAllData()
        assertThat(allCharacters).containsExactly(character)
    }

    @Test
    fun getAllData_returnsAllCharacters() = runTest {
        val character1 =
            CharacterEntity(
                id = "1",
                name = "Harry Potter",
                actor = "Daniel Radcliffe",
                dateOfBirth = "",
                "",
                true,
                "",
                ""
            )
        val character2 =
            CharacterEntity(
                id = "2",
                name = "Hermione Granger",
                actor = "Emma Watson",
                dateOfBirth = "",
                "",
                true,
                "",
                ""
            )
        dao.upsertCharacter(character1)
        dao.upsertCharacter(character2)

        val allCharacters = dao.getAllData()
        assertThat(allCharacters).containsExactly(character1, character2)
    }

    @Test
    fun searchCharactersWithFirstName_findsCorrectCharacters() = runTest {
        val character =
            CharacterEntity(
                id = "1",
                name = "Harry Potter",
                actor = "Daniel Radcliffe",
                dateOfBirth = "",
                "",
                true,
                "",
                ""
            )
        dao.upsertCharacter(character)

        val searchResult = dao.searchCharacters("Harry")
        assertThat(searchResult).containsExactly(character)
    }

    @Test
    fun searchCharactersActorWithSurname_findsCorrectCharacters() = runTest {
        val character =
            CharacterEntity(
                id = "1",
                name = "Harry Potter",
                actor = "Daniel Radcliffe",
                dateOfBirth = "",
                "",
                true,
                "",
                ""
            )
        dao.upsertCharacter(character)

        val searchResult = dao.searchCharacters("Radcliffe")
        assertThat(searchResult).containsExactly(character)
    }

    @Test
    fun searchCharactersWithSingleCharacter_findsCorrectCharacters() = runTest {
        val character =
            CharacterEntity(
                id = "1",
                name = "Harry Potter",
                actor = "Daniel Radcliffe",
                dateOfBirth = "",
                "",
                true,
                "",
                ""
            )
        dao.upsertCharacter(character)

        val searchResult = dao.searchCharacters("a")
        assertThat(searchResult).containsExactly(character)
    }

    @Test
    fun searchCharactersWithBadString_findsNothing() = runTest {
        val character =
            CharacterEntity(
                id = "1",
                name = "Harry Potter",
                actor = "Daniel Radcliffe",
                dateOfBirth = "",
                "",
                true,
                "",
                ""
            )
        dao.upsertCharacter(character)

        val searchResult = dao.searchCharacters("qqqqqqqqqqqqqqqqqqq")
        assertThat(searchResult).isEmpty()
    }

    @Test
    fun getCharacterById_returnsCorrectCharacter() = runTest {
        val character =
            CharacterEntity(
                id = "1",
                name = "Harry Potter",
                actor = "Daniel Radcliffe",
                dateOfBirth = "",
                "",
                true,
                "",
                ""
            )
        dao.upsertCharacter(character)

        val retrievedCharacter = dao.getCharacterById("1")
        assertThat(retrievedCharacter).isEqualTo(character)
    }
}
