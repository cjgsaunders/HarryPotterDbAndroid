package com.example.harrypotterapp.di

import android.content.Context
import androidx.room.Room
import com.example.harrypotterapp.data.database.CharacterDao
import com.example.harrypotterapp.data.database.CharacterDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CharacterDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            CharacterDatabase::class.java,
            "hogwarts_db1"
        ).fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideCharacterDao(database: CharacterDatabase): CharacterDao = database.dao()
}
