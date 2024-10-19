package com.example.harrypotterapp.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.harrypotterapp.R
import com.example.harrypotterapp.presentation.detailScreen.DetailScreenContent
import com.example.harrypotterapp.presentation.listScreen.ListScreenContent
import com.example.harrypotterapp.presentation.theme.LocalColorScheme
import com.example.harrypotterapp.presentation.theme.getColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HarryPotterDbApp(navController: NavHostController = rememberNavController()) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen =
        AppScreen.valueOf(
            backStackEntry?.destination?.route?.substringBefore("/") ?: AppScreen.Start.name,
        )
    val scrollBehavior =
        if (currentScreen == AppScreen.Start) {
            TopAppBarDefaults.enterAlwaysScrollBehavior()
        } else {
            TopAppBarDefaults.pinnedScrollBehavior()
        }

    CompositionLocalProvider(LocalColorScheme provides getColorScheme()) {
        Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
            GlobalTopAppBar(
                scrollBehavior = scrollBehavior,
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
            )
        }) { innerPadding ->

            NavHost(
                navController = navController,
                startDestination = AppScreen.Start.name,
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
            ) {
                composable(route = AppScreen.Start.name) {
                    ListScreenContent(
                        onCardClicked = { characterId ->
                            navController.navigate("${AppScreen.DetailScreen.name}/$characterId")
                        },
                    )
                }
                composable(route = "${AppScreen.DetailScreen.name}/{characterId}") { backStackEntry ->
                    val characterId = backStackEntry.arguments?.getString("characterId")
                    DetailScreenContent(characterId = characterId)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlobalTopAppBar(
    currentScreen: AppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors =
            TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = LocalColorScheme.current.topAppBarColor,
                titleContentColor = LocalColorScheme.current.textColor,
                scrolledContainerColor = LocalColorScheme.current.topAppBarColor,
            ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button),
                        tint = LocalColorScheme.current.textColor,
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior,
    )
}

enum class AppScreen(
    @StringRes val title: Int,
) {
    Start(title = R.string.app_name),
    DetailScreen(title = R.string.detailed_view),
}
