package com.example.harrypotterapp.data.repository

import com.example.harrypotterapp.data.CharacterApi
import com.example.harrypotterapp.data.CharacterDto
import com.example.harrypotterapp.domain.Resource
import com.example.harrypotterapp.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: CharacterApi
) : CharacterRepository {

    override suspend fun getCharacterData(): Flow<Resource<List<CharacterDto>>> = flow {
        try {
            emit(
                Resource.Success(
                    api.getCharacter()
                )
            )
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }.catch { e ->
        emit(Resource.Error(e.message ?: "Unknown error"))
    }

}