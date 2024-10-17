package com.example.harrypotterapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.harrypotterapp.domain.models.CharacterModel
import com.example.harrypotterapp.presentation.previewproviders.CharacterModelPreviewProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SearchComponent(onSearchTextChange: (String) -> Unit, size: Int) {
    val searchTextState = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainer)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            value = searchTextState.value,
            onValueChange = { updatedText ->
                searchTextState.value = updatedText
                onSearchTextChange.invoke(updatedText)
            },
            placeholder = { Text("Search character, actor, species, house") }
        )
        Text(modifier = Modifier.padding(start = 10.dp), text = "Results: $size")
    }
}

@Composable
fun ScreenComponent(
    characters: List<CharacterModel>,
    onSearchTextChange: (String) -> Unit,
    refresh: () -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 0.dp)) {
        SearchComponent(onSearchTextChange, characters.size)
        GridListComponent(characters, refresh)

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GridListComponent(characters: List<CharacterModel>, refresh: () -> Unit) {
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
        }){
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 400.dp),
                modifier = Modifier.fillMaxSize(),
                state = rememberLazyGridState()
            ) {
                items(characters) { character ->
                    CharacterCard(character)
                }
            }
        }
}


@Composable
private fun CharacterCard(character: CharacterModel) {
    Card(
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.outlinedCardElevation(),
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp,
                end = 16.dp
            )
            .fillMaxWidth()
    ) {
        //Row() {
//                    AsyncImage(
//                        model = characters[i].characterImage,
//                        contentDescription = null,
//                        placeholder = painterResource(R.drawable.logo),
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier.size(140.dp)
//
//                    )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp, top = 4.dp, start = 10.dp, end = 10.dp)
                .fillMaxWidth()
        ) {
            // Title
            Text(
                character.characterName,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp)
            )

            // Contents
            Row {
                Column {
                    Text(
                        text = "Played by: ${character.actor}",
                        style = TextStyle(fontSize = 12.sp),
                        modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)
                    )
                    Text(
                        text = "Species: ${character.species}",
                        style = TextStyle(fontSize = 12.sp),
                        modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)
                    )
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .size(50.dp) // This makes the Box a square
                            .background(color = character.houseColour)
                            .align(Alignment.End)
                            .semantics { contentDescription = character.houseNameLabel }
                    )
                }

            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun SuccessComponentPreview(
    @PreviewParameter(CharacterModelPreviewProvider::class)
    characters: List<CharacterModel>
) {
    ScreenComponent(characters, {}, {})
}
