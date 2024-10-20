package com.example.harrypotterapp.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface CharacterDao {
    @Upsert
    suspend fun upsertCharacter(characterModel: CharacterEntity)

    @Query("SELECT * from CHARACTER")
    fun getAllData(): List<CharacterEntity>

    @Query("SELECT * FROM CHARACTER WHERE name LIKE '%' || :searchText || '%' OR actor LIKE '%' || :searchText || '%'")
    fun searchCharacters(searchText: String): List<CharacterEntity>

    @Query("SELECT * FROM CHARACTER WHERE id = :characterId")
    fun getCharacterById(characterId: String): CharacterEntity
}
