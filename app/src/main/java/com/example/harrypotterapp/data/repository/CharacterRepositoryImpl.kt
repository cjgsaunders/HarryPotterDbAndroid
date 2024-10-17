package com.example.harrypotterapp.data.repository

import com.example.harrypotterapp.data.CharacterApi
import com.example.harrypotterapp.data.database.CharacterDao
import com.example.harrypotterapp.data.database.toDb
import com.example.harrypotterapp.data.database.toDomainModel
import com.example.harrypotterapp.data.mappers.toCharacterModel
import com.example.harrypotterapp.data.mappers.toCharacterModelList
import com.example.harrypotterapp.domain.Resource
import com.example.harrypotterapp.domain.models.CharacterModel
import com.example.harrypotterapp.domain.repository.CharacterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


class CharacterRepositoryImpl @Inject constructor(
    private val api: CharacterApi, private val dao: CharacterDao
) : CharacterRepository {


//    override suspend fun getCharacterData(): Flow<Resource<List<CharacterModel>>> = flow {
//        emit(Resource.Loading)
//
//        emit(Resource.Success(dao.getAllData().map {it.toDomainModel()  }))
//    }.catch { e ->
//        emit(Resource.Error(e.message ?: "Unknown error"))
//    }.stateIn(
//        scope = CoroutineScope(Dispatchers.IO),
//        started = SharingStarted.WhileSubscribed(5000),
//        initialValue = Resource.Loading
//    )

    // WORKING
    private suspend fun updateCharactersFromNetwork() {
        api.getCharacter().let { characters -> characters.toCharacterModelList().map { it.toDb() } }
            .also { characters -> characters.map { dao.upsertCharacter(it) } }
    }

    override suspend fun getCharacterData(): Flow<Resource<List<CharacterModel>>> =
        flow {
            emit(Resource.Loading)
            emit(
                Resource.Success(
                    dao.getAllData().map { characterEntity -> characterEntity.toDomainModel() })
            )
        }.onStart {
            try {
                updateCharactersFromNetwork()
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "unknown error loading data"))
            }
        }.catch { e ->
            emit(Resource.Error(e.message ?: "Unknown error"))
        }.stateIn(
            scope = CoroutineScope(Dispatchers.IO),
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Resource.Loading
        )
}