package com.example.harrypotterapp.presentation.detailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.harrypotterapp.domain.models.CharacterModel
import com.example.harrypotterapp.presentation.listScreen.CardTitleGradientBackground
import com.example.harrypotterapp.presentation.previewproviders.CharacterModelPreviewProvider
import com.example.harrypotterapp.presentation.theme.LocalColorScheme
import com.example.harrypotterapp.presentation.theme.getColorScheme

@Composable
fun DetailScreenContent(character: CharacterModel) {
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
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
                .fillMaxWidth().fillMaxHeight()
        ) {
            CardTitleGradientBackground(character)
            Text(character.characterName, color = LocalColorScheme.current.textColor)
            Text(character.actor, color = LocalColorScheme.current.textColor)
            Text(character.species, color = LocalColorScheme.current.textColor)
            Text(character.houseNameLabel, color = LocalColorScheme.current.textColor)
            Text(character.dateOfBirth ?: "" , color = LocalColorScheme.current.textColor)
            Text(character.alive.toString(), color = LocalColorScheme.current.textColor)
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