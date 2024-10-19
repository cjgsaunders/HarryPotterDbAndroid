import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.example.harrypotterapp.domain.models.CharacterModel
import com.example.harrypotterapp.presentation.listScreen.CharacterCard
import com.example.harrypotterapp.presentation.previewproviders.CharacterModelPreviewProvider

@Preview
@Composable
fun CharacterCardPreview(
    @PreviewParameter(CharacterModelPreviewProvider::class)
    characters: List<CharacterModel>,
) {
    CharacterCard(characters.first(), {})
}
