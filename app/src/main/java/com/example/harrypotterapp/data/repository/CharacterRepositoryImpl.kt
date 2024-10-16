package com.example.harrypotterapp.data.repository

import com.example.harrypotterapp.data.CharacterApi
import com.example.harrypotterapp.data.CharacterDto
import com.example.harrypotterapp.data.mappers.toCharacterModel
import com.example.harrypotterapp.data.mappers.toCharacterModelList
import com.example.harrypotterapp.domain.Resource
import com.example.harrypotterapp.domain.models.CharacterModel
import com.example.harrypotterapp.domain.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


//class CharacterRepositoryImpl @Inject constructor(
//    private val api: CharacterApi, private val dao: CharacterDao
//) : CharacterRepository {
//
//    override suspend fun getCharacterData(): Flow<Resource<List<CharacterModel>>> = flow {
//        emit(Resource.Loading)
//        try {
//            val apiData = api.getCharacter()
//            apiData.map { dao.upsertCharacter(it.toCharacterModel()) }
//            emit(Resource.Success(apiData.toCharacterModelList()))
//
//        } catch (e: Exception) {
//            val localData = dao.getAllData()
//            if (localData.isNotEmpty()) {
//                emit(Resource.Success(localData.map { it }))
//            } else {
//                emit(Resource.Error(e.message ?: "Unknown error"))
//            }
//        }
//    }.catch { e ->
//        emit(Resource.Error(e.message ?: "Unknown error"))
//    }.flowOn(Dispatchers.IO)
//
//
////    private suspend fun updateCharactersFromNetwork() {
////        api.getCharacter()
////            .let { characters -> characters.toCharacterModelList().map { it.toDb() } }
////            .also { characters -> characters.map { dao.upsertCharacter(it) } }
////    }
////
////    override suspend fun getCharacterData(): Flow<Resource<List<CharacterModel>>> =
////        dao.getAllData()
////            .onStart {
////                //updateCharactersFromNetwork()
////                api.getCharacter()
////            }
////            .map { entities ->
////                val models = entities.map { entity ->
////                    entity.toDomainModel()
////                }
////                Resource.Success(models)
////            }
////            .catch { exception ->
////                //emit(Resource.Error(exception.message ?: "Unknown error"))
////            }
////            .stateIn(
////                scope = CoroutineScope(Dispatchers.IO),
////                started = SharingStarted.WhileSubscribed(5000),
////                initialValue = Resource.Loading
////            )

class CharacterRepositoryImpl @Inject constructor(
    private val api: CharacterApi
) : CharacterRepository {

    override suspend fun getCharacterData(): Flow<Resource<List<CharacterModel>>> = flow {
        try {
            emit(
                Resource.Success(
                    api.getCharacter().toCharacterModelList()
                )
            )
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }.catch { e ->
        emit(Resource.Error(e.message ?: "Unknown error"))
    }

}