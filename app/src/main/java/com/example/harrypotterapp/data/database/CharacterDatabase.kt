package com.example.harrypotterapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CharacterEntity::class],
    version = 3,
    exportSchema = false
)
abstract class CharacterDatabase : RoomDatabase() {
    abstract fun dao(): CharacterDao
}
