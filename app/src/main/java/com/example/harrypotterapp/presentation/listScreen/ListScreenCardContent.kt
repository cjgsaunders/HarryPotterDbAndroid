package com.example.harrypotterapp.presentation.listScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.harrypotterapp.domain.models.CharacterModel
import com.example.harrypotterapp.presentation.previewproviders.CharacterModelPreviewProvider
import com.example.harrypotterapp.presentation.theme.LocalColorScheme
import com.example.harrypotterapp.presentation.theme.getColorScheme

@Composable
fun CharacterCard(
    character: CharacterModel,
    onCardClicked: (String) -> Unit,
) {
    Card(
        shape = CardDefaults.shape,
        colors =
            CardDefaults.cardColors().copy(
                containerColor = LocalColorScheme.current.cardBackground,
                contentColor = LocalColorScheme.current.cardBackground,
                disabledContentColor = LocalColorScheme.current.cardBackground,
                disabledContainerColor = LocalColorScheme.current.cardBackground,
            ),
        elevation = CardDefaults.outlinedCardElevation(3.dp),
        modifier =
            Modifier
                .padding(
                    bottom = 6.dp,
                    top = 6.dp,
                    end = 16.dp,
                ).fillMaxWidth()
                .clickable {
                    onCardClicked.invoke(character.id)
                },
    ) {
        CardTitleGradientBackground(character)
        ListScreenCardDetails(character)
    }
}

@Composable
fun CardTitleGradientBackground(character: CharacterModel) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(
                    brush =
                        Brush.verticalGradient(
                            colors =
                                listOf(
                                    character.houseColour ?: LocalColorScheme.current.noHouseStartGradient,
                                    LocalColorScheme.current.cardBackground,
                                ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY,
                        ),
                ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = character.characterName,
            color = LocalColorScheme.current.textColor,
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
            modifier =
                Modifier
                    .padding(bottom = 20.dp, top = 20.dp, start = 10.dp, end = 10.dp),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
fun ListScreenCardDetails(character: CharacterModel) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(LocalColorScheme.current.cardBackground),
    ) {
        FlowRow {
            Text(
                color = LocalColorScheme.current.dataSubtitle,
                text = "Played by: ",
                style = TextStyle(fontSize = 12.sp),
                modifier =
                    Modifier
                        .padding(start = 20.dp, bottom = 10.dp, top = 10.dp)
                        .widthIn(min = 100.dp),
            )
            Text(
                color = LocalColorScheme.current.textColor,
                text = character.actor,
                style = TextStyle(fontSize = 12.sp),
                modifier =
                    Modifier
                        .padding(start = 20.dp, bottom = 10.dp, top = 10.dp, end = 20.dp),
            )
        }
        FlowRow {
            Text(
                color = LocalColorScheme.current.dataSubtitle,
                text = "Species:",
                style = TextStyle(fontSize = 12.sp),
                modifier =
                    Modifier
                        .padding(start = 20.dp, bottom = 10.dp, top = 10.dp)
                        .widthIn(min = 100.dp),
            )
            Text(
                color = LocalColorScheme.current.textColor,
                text = character.species,
                style = TextStyle(fontSize = 12.sp),
                modifier =
                    Modifier
                        .padding(start = 20.dp, bottom = 10.dp, top = 10.dp, end = 20.dp),
            )
        }
    }
}

@PreviewLightDark
@PreviewFontScaleCustom
@Composable
fun CharacterCardPreview(
    @PreviewParameter(CharacterModelPreviewProvider::class)
    characters: List<CharacterModel>,
) {
    CompositionLocalProvider(LocalColorScheme provides getColorScheme()) {
        CharacterCard(characters.first()) {}
    }
}
