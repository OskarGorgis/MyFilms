package com.example.myfilms.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myfilms.MainScreen
import com.example.myfilms.ui.EditStartScreen
import com.example.myfilms.ui.FilmCreateDestination
import com.example.myfilms.ui.FilmCreateScreen
import com.example.myfilms.ui.FilmDetailsDestination
import com.example.myfilms.ui.FilmDetailsScreen
import com.example.myfilms.ui.FilmEditDestination
import com.example.myfilms.ui.FilmEditScreen
import com.example.myfilms.ui.FilmListScreen
import com.example.myfilms.ui.view_models.FilmViewModel
import com.example.myfilms.ui.StartScreen


@Composable
fun FilmNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: FilmViewModel,
    innerPadding: PaddingValues = PaddingValues(8.dp)
) {
    //val viewModel: FilmViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val uiState by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = MainScreen.Start.name,
        modifier = androidx.compose.ui.Modifier.padding(innerPadding)
    ) {
        composable(route = MainScreen.Start.name) {
            StartScreen(
                uiState = uiState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            )
        }
        composable(route = MainScreen.EditStart.name) {
            EditStartScreen(
                uiState = uiState,
                goBack = { navController.popBackStack() }
            )
        }
        composable(route = MainScreen.ListOfFilms.name) {
            FilmListScreen(
                navigateToItemCreate = { navController.navigate(MainScreen.Create.name) },
                navigateToItemDetails = { navController.navigate("${MainScreen.Details.name}/${it}") }
            )
        }
        composable(route = FilmCreateDestination.route) {
            FilmCreateScreen(navigateBack = { navController.popBackStack() })
        }
        composable(
            route = FilmDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(FilmDetailsDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            FilmDetailsScreen(
                navigateToEditItem = { navController.navigate("${FilmEditDestination.route}/$it") },
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = FilmEditDestination.routeWithArgs,
            arguments = listOf(navArgument(FilmEditDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            FilmEditScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

    }
}