package com.example.harrypotterapp.presentation.detailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.harrypotterapp.R
import com.example.harrypotterapp.domain.models.CharacterModel
import com.example.harrypotterapp.presentation.listScreen.CardTitleGradientBackground
import com.example.harrypotterapp.presentation.previewproviders.CharacterModelPreviewProvider
import com.example.harrypotterapp.presentation.theme.LocalColorScheme
import com.example.harrypotterapp.presentation.theme.getColorScheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailScreenContent(character: CharacterModel) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(LocalColorScheme.current.startBackground)
    ) {


        Card(
            shape = CardDefaults.shape, colors = CardDefaults.cardColors().copy(
                containerColor = LocalColorScheme.current.cardBackground,
                contentColor = LocalColorScheme.current.cardBackground,
                disabledContentColor = LocalColorScheme.current.cardBackground,
                disabledContainerColor = LocalColorScheme.current.cardBackground
            ), elevation = CardDefaults.outlinedCardElevation(3.dp), modifier = Modifier
                .padding(
                    start = 16.dp, bottom = 6.dp, top = 6.dp, end = 16.dp
                )
                .fillMaxWidth()
        ) {
            CardTitleGradientBackground(character)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                var titleColumWidth by remember { mutableStateOf(65.dp) }
                var dataColumnWidth by remember { mutableStateOf(65.dp) }

                Column(Modifier.fillMaxWidth(0.5f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 20.dp, bottom = 20.dp, top = 10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .background(if (character.alive) Color.Green else Color.Red)
                        )
                        Spacer(modifier = Modifier.width(8.dp)) // Add some space between the box and the text
                        Text(
                            text = "${stringResource(if (character.alive) R.string.alive else R.string.dead)} - ${character.species}",
                            color = LocalColorScheme.current.textColor
                        )
                    }

                    FlowRow {
                        Text(
                            stringResource(R.string.played_by),
                            color = LocalColorScheme.current.dataSubtitle,
                            modifier = Modifier
                                .padding(start = 20.dp, bottom = 10.dp)
                                .widthIn(min = with(LocalDensity.current) { titleColumWidth.value.toDp() })
                        )
                        Text(
                            character.actor,
                            color = LocalColorScheme.current.textColor,
                            modifier = Modifier
                                .padding(start = 20.dp, bottom = 10.dp)
                                .widthIn(min = with(LocalDensity.current) { dataColumnWidth.value.toDp() })
                                .onSizeChanged { size ->
                                    dataColumnWidth = size.width.dp
                                }
                        )
                    }
                    FlowRow {
                        Text(
                            stringResource(R.string.house),
                            color = LocalColorScheme.current.dataSubtitle,
                            modifier = Modifier
                                .padding(start = 20.dp, bottom = 10.dp)
                                .widthIn(min = with(LocalDensity.current) { titleColumWidth.value.toDp() })
                        )
                        Text(
                            character.houseNameLabel,
                            color = LocalColorScheme.current.textColor,
                            modifier = Modifier
                                .padding(start = 20.dp, bottom = 10.dp)
                                .widthIn(min = with(LocalDensity.current) { dataColumnWidth.value.toDp() })
                        )
                    }
                    FlowRow {
                        Text(
                            stringResource(R.string.date_of_birth),
                            color = LocalColorScheme.current.dataSubtitle,
                            modifier = Modifier
                                .padding(start = 20.dp, bottom = 10.dp)
                                .widthIn(min = with(LocalDensity.current) { titleColumWidth.value.toDp() })
                                .onSizeChanged { size ->
                                    titleColumWidth = size.width.dp
                                }
                        )
                        Text(
                            character.dateOfBirth ?: stringResource(R.string.unknown),
                            color = LocalColorScheme.current.textColor,
                            modifier = Modifier
                                .padding(start = 20.dp, bottom = 10.dp)
                                .widthIn(min = with(LocalDensity.current) { dataColumnWidth.value.toDp() })
                        )
                    }


                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    AsyncImage(
                        alignment = Alignment.TopEnd,
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(character.image)
                            .build(),
                        error = painterResource(R.drawable.no_image),
                        contentDescription = "image of ${character.characterName}",
                        modifier = Modifier.aspectRatio(3f / 4f),
                        contentScale = ContentScale.Crop

                    )
                }
            }


        }


    }
}

@PreviewLightDark
@Composable
fun DetailScreenContentPreview(
    @PreviewParameter(CharacterModelPreviewProvider::class) characters: List<CharacterModel>
) {
    CompositionLocalProvider(LocalColorScheme provides getColorScheme()) {
        DetailScreenContent(characters.first())
    }


}