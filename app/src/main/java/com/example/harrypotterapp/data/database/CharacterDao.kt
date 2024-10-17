package com.example.harrypotterapp.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.harrypotterapp.data.CharacterDto
import com.example.harrypotterapp.domain.Resource
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Upsert
    suspend fun upsertCharacter(characterModel: CharacterEntity)

    @Query("SELECT * from CHARACTER")
    fun getAllData(): List<CharacterEntity>

    @Query("SELECT * FROM CHARACTER WHERE characterName LIKE :searchText OR actor LIKE :searchText")
    fun searchCharacters(searchText: String): List<CharacterEntity>
}