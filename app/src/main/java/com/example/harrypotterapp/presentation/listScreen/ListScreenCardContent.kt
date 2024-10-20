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
import com.example.harrypotterapp.domain.CharacterDomainModel
import com.example.harrypotterapp.presentation.previewproviders.CharacterModelPreviewProvider
import com.example.harrypotterapp.presentation.previewproviders.PreviewFontScaleCustom
import com.example.harrypotterapp.presentation.theme.LocalColorScheme
import com.example.harrypotterapp.presentation.theme.card_elevation
import com.example.harrypotterapp.presentation.theme.card_vertical_padding
import com.example.harrypotterapp.presentation.theme.data_horizontal_padding
import com.example.harrypotterapp.presentation.theme.data_title_min_width
import com.example.harrypotterapp.presentation.theme.data_vertical_padding
import com.example.harrypotterapp.presentation.theme.getColorScheme
import com.example.harrypotterapp.presentation.theme.screen_margin

@Composable
fun CharacterCard(character: CharacterDomainModel, onCardClicked: (String) -> Unit) {
    Card(
        shape = CardDefaults.shape,
        colors =
        CardDefaults.cardColors().copy(
            containerColor = LocalColorScheme.current.cardBackground,
            contentColor = LocalColorScheme.current.cardBackground,
            disabledContentColor = LocalColorScheme.current.cardBackground,
            disabledContainerColor = LocalColorScheme.current.cardBackground
        ),
        elevation = CardDefaults.outlinedCardElevation(card_elevation),
        modifier =
        Modifier
            .padding(
                bottom = card_vertical_padding,
                top = card_vertical_padding,
                end = screen_margin
            ).fillMaxWidth()
            .clickable {
                onCardClicked.invoke(character.id)
            }
    ) {
        CardTitleGradientBackground(character)
        ListScreenCardDetails(character)
    }
}

@Composable
fun CardTitleGradientBackground(character: CharacterDomainModel) {
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
                        LocalColorScheme.current.cardBackground
                    ),
                    startY = 0f,
                    endY = Float.POSITIVE_INFINITY
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = character.characterName,
            color = LocalColorScheme.current.textColor,
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
            modifier =
            Modifier
                .padding(bottom = 20.dp, top = 20.dp, start = 10.dp, end = 10.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
fun ListScreenCardDetails(character: CharacterDomainModel) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(LocalColorScheme.current.cardBackground)
    ) {
        FlowRow {
            Text(
                color = LocalColorScheme.current.dataSubtitle,
                text = "Played by: ",
                style = TextStyle(fontSize = 12.sp),
                modifier =
                Modifier
                    .padding(start = data_horizontal_padding, bottom = data_vertical_padding, top = data_vertical_padding)
                    .widthIn(min = 100.dp)
            )
            Text(
                color = LocalColorScheme.current.textColor,
                text = character.actor,
                style = TextStyle(fontSize = 12.sp),
                modifier =
                Modifier
                    .padding(
                        start = data_horizontal_padding,
                        bottom = data_vertical_padding,
                        top = data_vertical_padding,
                        end = data_horizontal_padding
                    )
            )
        }
        FlowRow {
            Text(
                color = LocalColorScheme.current.dataSubtitle,
                text = "Species:",
                style = TextStyle(fontSize = 12.sp),
                modifier =
                Modifier
                    .padding(start = data_horizontal_padding, bottom = data_vertical_padding, top = data_vertical_padding)
                    .widthIn(min = data_title_min_width)
            )
            Text(
                color = LocalColorScheme.current.textColor,
                text = character.species,
                style = TextStyle(fontSize = 12.sp),
                modifier =
                Modifier
                    .padding(
                        start = data_horizontal_padding,
                        bottom = data_vertical_padding,
                        top = data_vertical_padding,
                        end = data_horizontal_padding
                    )
            )
        }
    }
}

@PreviewLightDark
@PreviewFontScaleCustom
@Composable
fun CharacterCardPreview(
    @PreviewParameter(CharacterModelPreviewProvider::class)
    characters: List<CharacterDomainModel>
) {
    CompositionLocalProvider(LocalColorScheme provides getColorScheme()) {
        CharacterCard(characters.first()) {}
    }
}
