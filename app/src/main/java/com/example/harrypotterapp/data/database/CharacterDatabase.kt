package com.example.harrypotterapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.harrypotterapp.data.CharacterDto
import com.example.harrypotterapp.domain.models.CharacterModel

@Database(
    entities = [CharacterDto::class],
    version = 1,
    exportSchema = false
)
abstract class CharacterDatabase : RoomDatabase() {

    abstract fun dao(): CharacterDao
}