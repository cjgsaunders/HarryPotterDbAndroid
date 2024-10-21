package com.example.harrypotterapp

import android.util.Log
import app.cash.turbine.test
import com.example.harrypotterapp.data.database.CharacterEntity
import com.example.harrypotterapp.domain.CharacterDomainModel
import com.example.harrypotterapp.domain.CharacterRepository
import com.example.harrypotterapp.domain.Resource
import com.example.harrypotterapp.presentation.listScreen.ListScreenViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ListScreenViewModelTest {

    private val repository: CharacterRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: ListScreenViewModel

    private val characterEntity: CharacterEntity = mockk(relaxed = true)
    private val characterModelList = listOf(
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
    )

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
        Dispatchers.setMain(testDispatcher)
        viewModel = ListScreenViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun `given api returns loading state, when list state triggered, then state in viewmodel is loading resource`() = runTest {
        coEvery { repository.getCharacterData() } returns flowOf(Resource.Loading)

        viewModel.listScreenState.test {
            assertEquals(Resource.Loading, awaitItem())
        }
    }

    @Test
    fun `given api responds with data, when screenState is triggered, then return resource success with data`() = runTest {
        coEvery { repository.getCharacterData() } returns flowOf(Resource.Success(listOf(characterEntity)))
        viewModel.listScreenState.test {
            assertEquals(Resource.Success(characterModelList), awaitItem())
        }
    }

    @Test
    fun `given api responds with an error, when triggering screenState, then state is resource error with error message`() = runTest {
        val errorMessage = "Error fetching data"
        coEvery { repository.getCharacterData() } returns flowOf(Resource.Error(errorMessage))

        viewModel.listScreenState.test {
            assertEquals(Resource.Error(errorMessage), awaitItem())
        }
    }

    @Test
    fun `test search functionality1`() = runTest {
        val query = "Test"
        coEvery { repository.getCharacterData() } returns flowOf(Resource.Success(listOf(characterEntity)))
        coEvery { repository.searchCharacters(query) } returns flowOf(
            Resource.Loading,
            Resource.Success(listOf(characterEntity))
        )

        viewModel.onSearchTextChange(query)

        viewModel.filteredListScreenState.test {
            assertEquals(Resource.Loading, awaitItem())
            assertEquals(Resource.Success(characterModelList), awaitItem())
        }
    }

    @Test
    fun `given listState is triggered, when triggerRefresh called, then a second value is emitted`() = runTest {
        coEvery { repository.getCharacterData() } returns flowOf(Resource.Success(listOf(characterEntity)))

        viewModel.triggerRefresh()

        viewModel.listScreenState.test {
            assertEquals(Resource.Success(characterModelList), awaitItem())
            assertEquals(Resource.Success(characterModelList), awaitItem())
        }
    }
}
