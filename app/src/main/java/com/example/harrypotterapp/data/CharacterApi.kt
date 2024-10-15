package com.example.harrypotterapp.data

import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterApi  {
    @GET("api/characters")
    suspend fun getCharacter() : List<CharacterDto>
}