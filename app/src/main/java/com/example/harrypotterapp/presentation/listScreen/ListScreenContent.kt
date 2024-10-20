package com.example.harrypotterapp.presentation.listScreen

import FullScreenLoading
import android.widget.Toast
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.harrypotterapp.R
import com.example.harrypotterapp.domain.Resource

@Composable
fun ListScreenContent(listScreenViewModel: ListScreenViewModel = hiltViewModel(), onCardClicked: (String) -> Unit) {
    val state by listScreenViewModel.filteredListScreenState.collectAsStateWithLifecycle(
        initialValue = Resource.Loading
    )
    val toastError by listScreenViewModel.toastMessage.collectAsStateWithLifecycle()
    val searchText by listScreenViewModel.searchText.collectAsStateWithLifecycle()

    when (val data = state) {
        is Resource.Loading -> {
            FullScreenLoading()
        }

        is Resource.Success -> {
            ListScreenComponent(
                data.data,
                listScreenViewModel::onSearchTextChange,
                listScreenViewModel::triggerRefresh,
                onCardClicked,
                searchText
            )
        }

        is Resource.Error -> {
            Text(data.error)
        }
    }
    toastError?.let {
        ShowErrorToast(toastError ?: stringResource(R.string.unknown_error))
        listScreenViewModel.clearToast()
    }
}

@Composable
private fun ShowErrorToast(errorMessage: String) {
    val context = LocalContext.current
    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
}
