package com.example.harrypotterapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.harrypotterapp.ListScreenViewModel
import com.example.harrypotterapp.data.CharacterDto
import com.example.harrypotterapp.domain.Resource
import com.example.harrypotterapp.ui.theme.HarryPotterAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val listScreenViewModel: ListScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HarryPotterAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                    val state by listScreenViewModel.listScreenState.collectAsStateWithLifecycle()

                    when (val data = state) {
                        is Resource.Loading -> {
                            Text("loading")
                        }

                        is Resource.Success ->
                        {
                            CharactersList(data.data)
                        }

                        is Resource.Error ->
                        {
                            Text("Error")
                            Text("Error")
                            Text("Error")
                            Text("Error")
                            Text(data.error)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HarryPotterAppTheme {
        Greeting("Android")
    }
}

@Composable
fun CharactersList(characters: List<CharacterDto>) {
    LazyColumn(state = rememberLazyListState()) {
        items(characters) { character ->
            Card(
                shape = MaterialTheme.shapes.small,
                elevation =  CardDefaults.elevatedCardElevation(),
                modifier = Modifier
                    .padding(
                        bottom = 6.dp,
                        top = 6.dp
                    )
                    .fillMaxWidth()
            ) {
                Row() {
//                    AsyncImage(
//                        model = characters[i].characterImage,
//                        contentDescription = null,
//                        placeholder = painterResource(R.drawable.logo),
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier.size(140.dp)
//
//                    )
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            character.name,
                            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = character.id,
                            style = TextStyle(fontSize = 12.sp),
                            modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)
                        )
                    }
                }
            }
        }
    }
}

