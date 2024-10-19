package com.example.harrypotterapp.presentation.listScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.harrypotterapp.R
import com.example.harrypotterapp.domain.Resource
import com.example.harrypotterapp.domain.models.CharacterModel
import com.example.harrypotterapp.presentation.previewproviders.CharacterModelPreviewProvider
import com.example.harrypotterapp.presentation.theme.LocalColorScheme
import com.example.harrypotterapp.presentation.theme.getColorScheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SearchComponent(onSearchTextChange: (String) -> Unit, size: Int, searchText: String) {
    val searchTextState = remember { mutableStateOf(searchText) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp)
            .zIndex(1f)
            .background(LocalColorScheme.current.searchBoxBackground)
    ) {


        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(LocalColorScheme.current.searchBoxBackground),
            colors = TextFieldDefaults.colors().copy(
                unfocusedContainerColor = LocalColorScheme.current.searchBoxBackground,
                focusedContainerColor = LocalColorScheme.current.searchBoxBackground,
                focusedPlaceholderColor = LocalColorScheme.current.searchBoxPlaceholderText,
                unfocusedPlaceholderColor = LocalColorScheme.current.searchBoxPlaceholderText,
                focusedIndicatorColor = LocalColorScheme.current.topAppBarColor
            ),

            maxLines = 1,
            textStyle = TextStyle(color = LocalColorScheme.current.textColor),
            value = searchTextState.value,
            onValueChange = { updatedText ->
                searchTextState.value = updatedText
                onSearchTextChange.invoke(updatedText)
            },
            placeholder = { Text(stringResource(R.string.search_placeholder)) }
        )
        Text(
            modifier = Modifier.padding(start = 10.dp),
            color = LocalColorScheme.current.textColor,
            text = "${stringResource(R.string.results)} $size",
        )
    }
}

@PreviewLightDark
@PreviewFontScaleCustom
@Composable
fun SearchComponentWithTextPreview(
    @PreviewParameter(CharacterModelPreviewProvider::class)
    characters: List<CharacterModel>
) {
    CompositionLocalProvider(LocalColorScheme provides getColorScheme()) {
        SearchComponent({}, 10, "test")
    }

}

@PreviewLightDark
@PreviewFontScaleCustom
@Composable
fun SearchComponentEmptyPreview(
    @PreviewParameter(CharacterModelPreviewProvider::class)
    characters: List<CharacterModel>
) {
    CompositionLocalProvider(LocalColorScheme provides getColorScheme()) {
        SearchComponent({}, 1000, "")
    }

}