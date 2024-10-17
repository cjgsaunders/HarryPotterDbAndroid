package com.example.harrypotterapp.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
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
                    val toastError by listScreenViewModel.toastMessage.collectAsStateWithLifecycle()

                    when (val data = state) {
                        is Resource.Loading -> {
                            Text("loading")

                        }

                        is Resource.Success -> {
                            ScreenComponent(data.data, listScreenViewModel::onSearchTextChange, listScreenViewModel::triggerRefresh)
                        }

                        is Resource.Error -> {
                            Text(data.error)
                        }
                    }
                    toastError?.let { ShowErrorToast(toastError ?: "unkown error")
                    listScreenViewModel.clearToast()}
                }
            }
        }
    }
}

@Composable
fun ShowErrorToast(errorMessage: String) {
    val context = LocalContext.current
    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
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

