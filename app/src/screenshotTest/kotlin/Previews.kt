import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.harrypotterapp.domain.models.CharacterModel
import com.example.harrypotterapp.presentation.listScreen.CharacterCard
import com.example.harrypotterapp.presentation.previewproviders.CharacterModelPreviewProvider


@Preview
@Composable
fun CharacterCardPreview(
    @PreviewParameter(CharacterModelPreviewProvider::class)
    characters: List<CharacterModel>
) {
    CharacterCard(characters.first(), {})
}
