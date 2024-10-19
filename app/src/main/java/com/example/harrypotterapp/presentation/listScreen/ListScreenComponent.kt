package com.example.harrypotterapp.presentation.listScreen

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.harrypotterapp.domain.models.CharacterModel
import com.example.harrypotterapp.presentation.previewproviders.CharacterModelPreviewProvider
import com.example.harrypotterapp.presentation.theme.LocalColorScheme
import com.example.harrypotterapp.presentation.theme.getColorScheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ListScreenComponent(
    characters: List<CharacterModel>,
    onSearchTextChange: (String) -> Unit,
    refresh: () -> Unit,
    onCardClicked: (String) -> Unit,
    searchText: String
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 0.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        LocalColorScheme.current.startBackground,
                        LocalColorScheme.current.endBackground
                    ),
                    start = Offset(0f, Float.POSITIVE_INFINITY),
                    end = Offset(Float.POSITIVE_INFINITY, 0f)
                )
            )
    ) {
        SearchComponent(onSearchTextChange, characters.size, searchText)
        GridListComponent(characters, refresh, onCardClicked)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GridListComponent(
    characters: List<CharacterModel>,
    refresh: () -> Unit,
    onCardClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val refreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    PullToRefreshBox(
        modifier = Modifier.padding(start = 16.dp),
        state = refreshState,
        isRefreshing = isRefreshing,
        onRefresh = {
            coroutineScope.launch {
                isRefreshing = true
                refresh.invoke()
                delay(5000L)
                isRefreshing = false

            }
        }) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 400.dp),
            modifier = modifier.fillMaxSize(),
            state = rememberLazyGridState()
        ) {
            items(characters) { character ->
                CharacterCard(character, onCardClicked)
            }
        }
    }
}

@PreviewLightDark
@PreviewFontScaleCustom
@Composable
fun SuccessComponentPreview(
    @PreviewParameter(CharacterModelPreviewProvider::class)
    characters: List<CharacterModel>
) {
    CompositionLocalProvider(LocalColorScheme provides getColorScheme()) {
        ListScreenComponent(characters, {}, {}, {}, "")
    }
}


@Preview(name = "200%", fontScale = 2.5f)
annotation class PreviewFontScaleCustom


@Composable
fun ShowErrorToast(errorMessage: String) {
    val context = LocalContext.current
    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
}
