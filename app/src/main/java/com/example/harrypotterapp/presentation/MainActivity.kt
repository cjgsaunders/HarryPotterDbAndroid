package com.example.harrypotterapp.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.harrypotterapp.domain.Resource
import com.example.harrypotterapp.presentation.theme.HarryPotterAppTheme
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val listScreenViewModel: ListScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HarryPotterAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {  innerPadding ->
                    val state by listScreenViewModel.filteredListScreenState.collectAsStateWithLifecycle()

                    when (val data = state) {
                        is Resource.Loading -> {
                            Text("loading")
                        }

                        is Resource.Success -> {
                            ScreenComponent(data.data, listScreenViewModel::onSearchTextChange)
                        }

                        is Resource.Error -> {
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

