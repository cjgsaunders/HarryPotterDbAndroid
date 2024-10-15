package com.example.harrypotterapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.harrypotterapp.data.CharacterDto
import com.example.harrypotterapp.domain.models.CharacterModel
import com.example.harrypotterapp.presentation.previewproviders.CharacterModelPreviewProvider

@Composable
fun CharactersList(characters: List<CharacterModel>) {
    LazyColumn(state = rememberLazyListState()) {
        items(characters) { character ->
            Card(
                shape = MaterialTheme.shapes.small,
                elevation = CardDefaults.outlinedCardElevation(),
                modifier = Modifier
                    .padding(
                        bottom = 6.dp,
                        top = 6.dp,
                        start = 16.dp,
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
                    modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp, top = 4.dp, start = 10.dp, end = 10.dp).fillMaxWidth()
                ) {
                    Text(
                        character.name,
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier.align(Alignment.CenterHorizontally).padding(10.dp)
                    )
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
                        Column(modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                modifier = Modifier
                                    .padding(end = 20.dp)
                                    .size(50.dp) // This makes the Box a square
                                    .background(color = character.houseColour)
                                    .align(Alignment.End)
                            )
                        }

                    }

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
    CharactersList(characters)
}
