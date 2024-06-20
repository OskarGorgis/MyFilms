package com.example.myfilms.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myfilms.ui.navigation.NavigationDestination
import com.example.myfilms.ui.view_models.AppViewModelProvider
import com.example.myfilms.ui.view_models.FilmEditViewModel
import kotlinx.coroutines.launch

object FilmEditDestination : NavigationDestination {
    override val route = "Edit"
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmEditScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FilmEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold {
        innerPadding ->
        FilmCreateBody(
            filmUiState = viewModel.filmUiState,
            onFilmValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateFilm()
                    navigateBack()
                }
            },
            onBack = navigateBack,
            modifier = Modifier.padding(innerPadding)
        )
    }

}