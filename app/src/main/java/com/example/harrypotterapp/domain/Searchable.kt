package com.example.harrypotterapp.domain

interface Searchable {
    fun searchCharacterInfo(searchString: String): Boolean
}
