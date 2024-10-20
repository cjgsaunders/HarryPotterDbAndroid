import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.harrypotterapp.presentation.previewproviders.PreviewFontScaleCustom
import com.example.harrypotterapp.presentation.theme.LocalColorScheme
import com.example.harrypotterapp.presentation.theme.getColorScheme

@Composable
fun FullScreenError(error: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = error, fontSize = 18.sp, color = LocalColorScheme.current.textColor)
    }
}

@PreviewLightDark
@PreviewFontScaleCustom
@Composable
fun FullScreenErrorPreview() {
    CompositionLocalProvider(LocalColorScheme provides getColorScheme()) {
        Column(
            modifier = Modifier.background(
                brush =
                Brush.linearGradient(
                    colors =
                    listOf(
                        LocalColorScheme.current.startBackground,
                        LocalColorScheme.current.endBackground
                    )
                )
            )
        ) { FullScreenError("These are not the droids you are looking for...") }
    }
}
