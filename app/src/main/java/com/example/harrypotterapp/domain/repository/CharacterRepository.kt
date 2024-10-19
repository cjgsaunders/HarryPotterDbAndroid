package com.example.harrypotterapp.domain.repository

import com.example.harrypotterapp.domain.Resource
import com.example.harrypotterapp.domain.models.CharacterModel
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharacterData(): Flow<Resource<List<CharacterModel>>>

    suspend fun searchCharacters(searchText: String): Flow<Resource<List<CharacterModel>>>

    suspend fun getCharacterById(characterId: String): Flow<Resource<CharacterModel>>
}
