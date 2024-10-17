package com.example.harrypotterapp.presentation.detailScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.harrypotterapp.domain.Resource
import com.example.harrypotterapp.presentation.ListScreenViewModel


@Composable
fun DetailScreenComponent(
    detailScreenViewModel: DetailScreenViewModel = hiltViewModel(),
    characterId: String?
) {


    val state by detailScreenViewModel.singleCharacterScreenState.collectAsStateWithLifecycle()

    LaunchedEffect(characterId) {
        detailScreenViewModel.collectFavoriteNews(characterId ?: "")
    }



    when (val data = state) {
            is Resource.Loading -> {
                Text("loading")

            }

            is Resource.Success -> {
                DetailScreenContent(data.data)
            }

            is Resource.Error -> {
                Text(data.error)
            }
        }
    }
