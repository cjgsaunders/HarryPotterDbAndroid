package com.example.harrypotterapp.data.repository

import com.example.harrypotterapp.data.CharacterApi
import com.example.harrypotterapp.data.database.CharacterDao
import com.example.harrypotterapp.data.mappers.toCharacterModel
import com.example.harrypotterapp.data.mappers.toCharacterModelList
import com.example.harrypotterapp.data.toDb
import com.example.harrypotterapp.domain.Resource
import com.example.harrypotterapp.domain.models.CharacterModel
import com.example.harrypotterapp.domain.repository.CharacterRepository
import java.time.Duration
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.time.delay

class CharacterRepositoryImpl
@Inject
constructor(
    private val api: CharacterApi,
    private val dao: CharacterDao
) : CharacterRepository {
    private suspend fun updateCharactersFromNetwork() {
        api
            .getCharacter()
            .let { characters -> characters.map { it.toDb() } }
            .also { characters -> characters.map { dao.upsertCharacter(it) } }
    }

    override suspend fun getCharacterData(): Flow<Resource<List<CharacterModel>>> = flow {
        emit(Resource.Loading)
        emit(
            Resource.Success(
                dao.getAllData().toCharacterModelList()
            )
        )
    }.onStart {
        try {
            updateCharactersFromNetwork()
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "unknown error loading data"))
            delay(Duration.ofMillis(50))
        }
    }.catch { e ->
        emit(Resource.Error(e.message ?: "Unknown error"))
    }.flowOn(
        Dispatchers.IO
    )

    override suspend fun searchCharacters(searchText: String) = flow {
        try {
            emit(
                Resource.Success(
                    dao.searchCharacters(searchText).toCharacterModelList()
                )
            )
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "unknown error"))
        }
    }.flowOn(
        Dispatchers.IO
    )

    override suspend fun getCharacterById(characterId: String) = flow {
        try {
            emit(Resource.Success(dao.getCharacterById(characterId).toCharacterModel()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "unknown error"))
        }
    }.flowOn(
        Dispatchers.IO
    )
}
