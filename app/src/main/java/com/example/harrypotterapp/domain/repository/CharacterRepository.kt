package com.example.harrypotterapp.domain.repository

import com.example.harrypotterapp.data.CharacterDto
import com.example.harrypotterapp.domain.Resource
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharacterData(): Flow<Resource<List<CharacterDto>>>
}