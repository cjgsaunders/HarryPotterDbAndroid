package com.example.harrypotterapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

//    @Provides
//    @Singleton
//    fun provideDatabase(@ApplicationContext context: Context): CharacterDatabase {
//        return Room.databaseBuilder(
//            context.applicationContext,
//            CharacterDatabase::class.java,
//            "hogwarts_db"
//        ).fallbackToDestructiveMigration().build()
//    }
//
//    @Provides
//    fun provideCharacterDao(database: CharacterDatabase): CharacterDao {
//        return database.dao
//    }
}