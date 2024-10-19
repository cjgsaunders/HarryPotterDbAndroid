package com.example.harrypotterapp.data

import retrofit2.http.GET

interface CharacterApi {
    @GET("api/characters")
    suspend fun getCharacter(): List<CharacterDto>
}
