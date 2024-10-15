package com.example.harrypotterapp.domain.repository

import com.example.harrypotterapp.domain.Resource
import com.example.harrypotterapp.domain.models.CharacterModel
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharacterData(): Flow<Resource<List<CharacterModel>>>
}