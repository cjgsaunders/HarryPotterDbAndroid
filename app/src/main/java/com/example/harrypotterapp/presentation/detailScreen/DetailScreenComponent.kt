package com.example.harrypotterapp.presentation.detailScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.harrypotterapp.domain.Resource


@Composable
fun DetailScreenComponent(
    detailScreenViewModel: DetailScreenViewModel = hiltViewModel(),
    characterId: String?
) {


    val state by detailScreenViewModel.singleCharacterScreenStat1e(characterId ?: "").collectAsStateWithLifecycle(initialValue = Resource.Loading)

    when (val data = state) {
            is Resource.Success -> {
                DetailScreenContent(data.data)
            }

            is Resource.Error -> {
                Text(data.error)
            }

        is Resource.Loading -> {
            Text("loading")

        }
        }
    }
