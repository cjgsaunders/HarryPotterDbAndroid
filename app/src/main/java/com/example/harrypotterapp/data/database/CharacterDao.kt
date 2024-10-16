package com.example.harrypotterapp.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.harrypotterapp.data.CharacterDto

@Dao
interface CharacterDao {

    @Upsert
    suspend fun upsertCharacter(characterModel: CharacterDto)

    @Query("SELECT * from CHARACTER")
    fun getAllData(): List<CharacterDto>
}