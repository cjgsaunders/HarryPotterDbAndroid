package com.example.harrypotterapp

import android.util.Log
import com.example.harrypotterapp.data.database.CharacterEntity
import com.example.harrypotterapp.domain.CharacterDomainModel
import com.example.harrypotterapp.domain.CharacterRepository
import com.example.harrypotterapp.domain.Resource
import com.example.harrypotterapp.presentation.detailScreen.DetailScreenViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailScreenViewModelTest {

    private lateinit var viewModel: DetailScreenViewModel
    private val repository: CharacterRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
        Dispatchers.setMain(testDispatcher)
        viewModel = DetailScreenViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun `given repository emits success, when single character flow is triggered, return that character and success resource`() = runTest {
        val characterId = "1"
        val character: CharacterEntity = mockk(relaxed = true)
        val resultCharacter =
            CharacterDomainModel(
                id = "",
                characterName = "",
                actor = "Unknown",
                houseColour = null,
                dateOfBirth = null,
                image = "",
                alive = false,
                species = "Unknown",
                houseNameLabel = "Not in a Hogwarts House"
            )
        val resource = Resource.Success(character)
        coEvery { repository.getCharacterById(characterId) } returns flowOf<Resource<CharacterEntity>>(resource)

        val result = viewModel.singleCharacterScreenState(characterId).first()

        assertTrue(result is Resource.Success)
        assertEquals(resultCharacter, (result as Resource.Success).data)
    }

    @Test
    fun `given api returns error, when search is triggered, return error resource with that message`() = runTest {
        val characterId = "1"
        val error = Throwable(message = "Error")
        val resource = Resource.Error(error.message ?: "error")
        coEvery { repository.getCharacterById(characterId) } returns flowOf(resource)

        val result = viewModel.singleCharacterScreenState(characterId).first()

        assertTrue(result is Resource.Error)
        assertEquals(error.message, (result as Resource.Error).error)
    }

    @Test
    fun `given api returns loading state, when search is triggered, state in viewmodel is loading resource`() = runTest {
        val characterId = "1"
        val resource = Resource.Loading
        coEvery { repository.getCharacterById(characterId) } returns flowOf(resource)

        val result = viewModel.singleCharacterScreenState(characterId).first()

        assertTrue(result is Resource.Loading)
    }
}
