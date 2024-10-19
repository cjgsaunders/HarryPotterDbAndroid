package com.example.harrypotterapp.presentation.listScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.harrypotterapp.R
import com.example.harrypotterapp.domain.models.CharacterModel
import com.example.harrypotterapp.presentation.previewproviders.CharacterModelPreviewProvider
import com.example.harrypotterapp.presentation.theme.LocalColorScheme
import com.example.harrypotterapp.presentation.theme.getColorScheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SearchComponent(onSearchTextChange: (String) -> Unit, size: Int) {
    val searchTextState = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp)
            .zIndex(1f)
            .background(LocalColorScheme.current.searchBoxBackground)
    ) {


        TextField(
            modifier = Modifier.fillMaxWidth().background(LocalColorScheme.current.searchBoxBackground),
            colors = TextFieldDefaults.colors().copy(
                unfocusedContainerColor = LocalColorScheme.current.searchBoxBackground,
                focusedContainerColor = LocalColorScheme.current.searchBoxBackground,
                focusedPlaceholderColor = LocalColorScheme.current.searchBoxPlaceholderText,
                unfocusedPlaceholderColor = LocalColorScheme.current.searchBoxPlaceholderText,
                focusedIndicatorColor = LocalColorScheme.current.topAppBarColor
            ),

            maxLines = 1,
            textStyle = TextStyle(color = LocalColorScheme.current.textColor),
            value = searchTextState.value,
            onValueChange = { updatedText ->
                searchTextState.value = updatedText
                onSearchTextChange.invoke(updatedText)
            },
            placeholder = { Text(stringResource(R.string.search_placeholder)) }
        )
        Text(
            modifier = Modifier.padding(start = 10.dp),
            color = LocalColorScheme.current.textColor,
            text = "${stringResource(R.string.results)} $size",
        )
    }
}

@Composable
fun ListScreenContent(
    characters: List<CharacterModel>,
    onSearchTextChange: (String) -> Unit,
    refresh: () -> Unit,
    onCardClicked: (String) -> Unit,
    modifier: Modifier = Modifier
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
        SearchComponent(onSearchTextChange, characters.size)
        GridListComponent(characters, refresh, onCardClicked, modifier)
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

@Composable
fun CharacterCard(character: CharacterModel, onCardClicked: (String) -> Unit) {
    Card(
        shape = CardDefaults.shape,
        colors = CardDefaults.cardColors().copy(
            containerColor = LocalColorScheme.current.cardBackground,
            contentColor = LocalColorScheme.current.cardBackground,
            disabledContentColor = LocalColorScheme.current.cardBackground,
            disabledContainerColor = LocalColorScheme.current.cardBackground
        ),
        elevation = CardDefaults.outlinedCardElevation(3.dp),
        modifier = Modifier
            .padding(
                bottom = 6.dp, top = 6.dp, end = 16.dp
            )
            .fillMaxWidth()
            .clickable {
                onCardClicked.invoke(character.id)
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            character.houseColour ?: LocalColorScheme.current.noHouseStartGradient,
                            LocalColorScheme.current.cardBackground
                        ),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        ) {
            // Title
            Text(
                character.characterName,
                color = LocalColorScheme.current.textColor,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 20.dp, top = 4.dp, start = 10.dp, end = 10.dp)
            )
        }

        // Contents
        Column(
            Modifier

                .fillMaxWidth()
                .background(LocalColorScheme.current.cardBackground)
        ) {
            Text(
                color = LocalColorScheme.current.textColor,
                text = "Played by: ${character.actor}",
                style = TextStyle(fontSize = 12.sp),
                modifier = Modifier.padding(start = 20.dp, bottom = 10.dp, top = 20.dp, end = 20.dp)
            )
            Text(
                color = LocalColorScheme.current.textColor,
                text = "Species: ${character.species}",
                style = TextStyle(fontSize = 12.sp),
                modifier = Modifier.padding(start = 20.dp, bottom = 20.dp, top = 10.dp, end = 20.dp)
            )
        }
    }
}

@PreviewLightDark
@Composable
fun CharacterCardPreview(
    @PreviewParameter(CharacterModelPreviewProvider::class)
    characters: List<CharacterModel>
) {
    CompositionLocalProvider(LocalColorScheme provides getColorScheme()) {
        CharacterCard(characters.first(), {})
    }

}


@PreviewLightDark
@Composable
fun SuccessComponentPreview(
    @PreviewParameter(CharacterModelPreviewProvider::class)
    characters: List<CharacterModel>
) {
    CompositionLocalProvider(LocalColorScheme provides getColorScheme()) {
        ListScreenContent(characters, {}, {}, {})
    }


}

