package com.example.harrypotterapp.domain

sealed class Resource<out Data> {
    data object Loading : Resource<Nothing>()

    data class Success<out D>(
        val data: D
    ) : Resource<D>()

    data class Error(
        val error: String
    ) : Resource<Nothing>()
}
