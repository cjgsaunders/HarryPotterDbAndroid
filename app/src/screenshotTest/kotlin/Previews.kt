import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.example.harrypotterapp.presentation.previewproviders.PreviewFontScaleCustom
import com.example.harrypotterapp.domain.models.CharacterModel
import com.example.harrypotterapp.presentation.detailScreen.DetailScreenComponent
import com.example.harrypotterapp.presentation.listScreen.CharacterCard
import com.example.harrypotterapp.presentation.listScreen.ListScreenComponent
import com.example.harrypotterapp.presentation.previewproviders.CharacterModelPreviewProvider
import com.example.harrypotterapp.presentation.theme.LocalColorScheme
import com.example.harrypotterapp.presentation.theme.getColorScheme

@PreviewLightDark
@PreviewFontScaleCustom
@Composable
fun DetailScreenContentPreview(
    @PreviewParameter(CharacterModelPreviewProvider::class) characters: List<CharacterModel>
) {
    CompositionLocalProvider(LocalColorScheme provides getColorScheme()) {
        DetailScreenComponent(characters.first())
    }
}

@PreviewLightDark
@PreviewFontScaleCustom
@Composable
fun CharacterCardPreview(
    @PreviewParameter(CharacterModelPreviewProvider::class)
    characters: List<CharacterModel>
) {
    CompositionLocalProvider(LocalColorScheme provides getColorScheme()) {
        CharacterCard(characters.first()) {}
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
