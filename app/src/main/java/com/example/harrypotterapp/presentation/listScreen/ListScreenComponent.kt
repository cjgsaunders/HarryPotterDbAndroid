package com.example.harrypotterapp.presentation.listScreen

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.harrypotterapp.domain.CharacterDomainModel
import com.example.harrypotterapp.presentation.previewproviders.CharacterModelPreviewProvider
import com.example.harrypotterapp.presentation.previewproviders.PreviewFontScaleCustom
import com.example.harrypotterapp.presentation.theme.LocalColorScheme
import com.example.harrypotterapp.presentation.theme.getColorScheme
import com.example.harrypotterapp.presentation.theme.screen_margin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ListScreenComponent(
    characters: List<CharacterDomainModel>,
    onSearchTextChange: (String) -> Unit,
    refresh: () -> Unit,
    onCardClicked: (String) -> Unit,
    searchText: String
) {
    Column {
        SearchComponent(onSearchTextChange, characters.size, searchText)
        GridListComponent(characters, refresh, onCardClicked)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GridListComponent(
    characters: List<CharacterDomainModel>,
    refresh: () -> Unit,
    onCardClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val refreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    PullToRefreshBox(
        modifier = Modifier.padding(start = screen_margin),
        state = refreshState,
        isRefreshing = isRefreshing,
        onRefresh = {
            coroutineScope.launch {
                isRefreshing = true
                refresh.invoke()
                delay(5000L)
                isRefreshing = false
            }
        }
    ) {
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
    characters: List<CharacterDomainModel>
) {
    CompositionLocalProvider(LocalColorScheme provides getColorScheme()) {
        ListScreenComponent(characters, {}, {}, {}, "")
    }
}
