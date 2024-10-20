package com.example.harrypotterapp.domain

import com.example.harrypotterapp.data.database.CharacterEntity
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharacterData(): Flow<Resource<List<CharacterEntity>>>

    suspend fun searchCharacters(searchText: String): Flow<Resource<List<CharacterEntity>>>

    suspend fun getCharacterById(characterId: String): Flow<Resource<CharacterEntity>>
}
