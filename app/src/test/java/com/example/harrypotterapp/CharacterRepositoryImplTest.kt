package com.example.harrypotterapp

import android.util.Log
import app.cash.turbine.test
import com.example.harrypotterapp.data.CharacterApi
import com.example.harrypotterapp.data.CharacterDto
import com.example.harrypotterapp.data.database.CharacterDao
import com.example.harrypotterapp.data.database.CharacterEntity
import com.example.harrypotterapp.data.repository.CharacterRepositoryImpl
import com.example.harrypotterapp.domain.Resource
import com.example.harrypotterapp.domain.models.CharacterModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CharacterRepositoryImplTest {

    private lateinit var repository: CharacterRepositoryImpl
    private val api: CharacterApi = mockk()
    private val dao: CharacterDao = mockk()

    @Before
    fun setUp() {
        repository = CharacterRepositoryImpl(api, dao)
    }

    @Test
    fun `getCharacterData should emit loading and success`() = runTest {
        val daoResult = listOf<CharacterEntity>()
        val apiResult = listOf<CharacterDto>()
        val result = listOf<CharacterModel>()

        coEvery { dao.getAllData() } returns daoResult
        coEvery { api.getCharacter() } returns apiResult

        repository.getCharacterData().test {
            assertEquals(Resource.Loading, awaitItem())
            assertEquals(Resource.Success(result), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `given a network error, when calling getAllData, load successfully from local database`() = runTest {
        val daoResult = listOf<CharacterEntity>()
        val apiResult = Exception("Network error")
        val result = listOf<CharacterModel>()

        coEvery { dao.getAllData() } returns daoResult
        coEvery { api.getCharacter() } throws apiResult

        repository.getCharacterData().test {
            val error = awaitItem()
            assert(error is Resource.Error && error.error == "Network error")
            assertEquals(Resource.Loading, awaitItem())
            assertEquals(Resource.Success(result), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `given a network error and database error, when calling getAllData, first emit network error then database error`() = runTest {
        val daoResult = Exception("Database error")
        val apiResult = Exception("Network error")

        coEvery { dao.getAllData() } throws daoResult
        coEvery { api.getCharacter() } throws apiResult

        repository.getCharacterData().test {
            val networkError = awaitItem()
            assert(networkError is Resource.Error && networkError.error == "Network error")
            assertEquals(Resource.Loading, awaitItem())
            val databaseError = awaitItem()
            assert(databaseError is Resource.Error && databaseError.error == "Database error")
            awaitComplete()
        }
    }

    @Test
    fun `given apis respond success, when search is called, respond Resource Success with data`() = runTest {
        val daoResult = listOf<CharacterEntity>()
        val result = listOf<CharacterModel>()
        val searchString = "Harry"

        coEvery { dao.searchCharacters(searchString) } returns daoResult
        coEvery { dao.searchCharacters("%$searchString%") } returns daoResult

        repository.searchCharacters(searchString).test {
            assertEquals(Resource.Success(result), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `given a database error, when searching, then emit error`() = runTest {
        val searchString = "Harry"
        val exception = Exception("Database error")

        coEvery { dao.searchCharacters(searchString) } throws exception

        repository.searchCharacters(searchString).test {
            val error = awaitItem()
            assert(error is Resource.Error && error.error == "Database error")
            awaitComplete()
        }
    }

    @Test
    fun `given dao responds with success, when get by id called, then return that character`() = runTest {
        val characterId = "1"
        mockkStatic(Log::class)
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
        val daoResult = CharacterEntity(
            id = "1",
            dateOfBirth = null,
            alive = true,
            name = "Harry Potter",
            actor = "Daniel Radcliffe",
            species = "human",
            house = "",
            image = ""
        )

        val result =
            CharacterModel("1", "Harry Potter", "Daniel Radcliffe", null, null, "", true, "Human", "Not in a Hogwarts House")

        coEvery { dao.getCharacterById(characterId) } returns daoResult

        repository.getCharacterById(characterId).test {
            assertEquals(Resource.Success(result), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `given a database error, when get by id called, then emit error`() = runTest {
        val characterId = "1"
        val exception = Exception("Database error")

        coEvery { dao.getCharacterById(characterId) } throws exception

        repository.getCharacterById(characterId).test {
            val error = awaitItem()
            assert(error is Resource.Error && error.error == "Database error")
            awaitComplete()
        }
    }
}
