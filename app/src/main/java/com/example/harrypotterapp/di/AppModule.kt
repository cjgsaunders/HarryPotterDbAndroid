package com.example.harrypotterapp.di

import com.example.harrypotterapp.data.CharacterApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideCharacterApi(json: Json): CharacterApi {
        return Retrofit.Builder().baseUrl("https://hp-api.onrender.com/").addConverterFactory(
            json.asConverterFactory(
                    "application/json; charset=UTF8".toMediaType()
                )
            ).build().create(CharacterApi::class.java)
    }
}
