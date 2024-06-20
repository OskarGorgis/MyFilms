package com.example.myfilms.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.myfilms.R
import com.example.myfilms.data.FilmEntity
import com.example.myfilms.ui.elements.RatingBar
import com.example.myfilms.ui.navigation.NavigationDestination
import com.example.myfilms.ui.view_models.AppViewModelProvider
import com.example.myfilms.ui.view_models.FilmDetailsUiState
import com.example.myfilms.ui.view_models.FilmDetailsViewModel
import kotlinx.coroutines.launch

object FilmDetailsDestination : NavigationDestination {
    override val route = "Details"
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmDetailsScreen(
    navigateToEditItem: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FilmDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState.collectAsState()
    val coroutingScope = rememberCoroutineScope()
    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        Box {
            DetailsBody(
                filmDetailsUiState = uiState.value,
                onDelete = {
                    coroutingScope.launch {
                        viewModel.deleteItem()
                        navigateBack()
                    }
                },
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            )

            Spacer(modifier = Modifier.padding(8.dp))
            
            Row(modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .padding(10.dp)
            ) {
                Button(onClick = { navigateToEditItem(uiState.value.filmDetails.id) }) {
                    Text(text = "Edit")
                }

                Spacer(modifier = Modifier.width(200.dp))

                Button(onClick = { navigateBack() }) {
                    Text(text = "Back")
                }
            }
        }

    }
}

@Composable
fun DetailsBody(
    filmDetailsUiState: FilmDetailsUiState,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
        // Obrazek
        Image(
            painter = rememberImagePainter(data = filmDetailsUiState.filmDetails.imageResource.toUri()),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tytuł
        Text(
            text = filmDetailsUiState.filmDetails.title
        )

        Spacer(modifier = Modifier.height(8.dp))

        // RatingBar (tylko do odczytu)
        RatingBar(
            rating = filmDetailsUiState.filmDetails.rating,
            stars = 5
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Opis
        Text(
            text = filmDetailsUiState.filmDetails.description
        )
    }
}

@Preview
@Composable
fun FilmDetailsScreenPreview() {
    // Przykładowy film do podglądu
    val sampleFilm = FilmEntity(
        id = 1,
        imageResource = "",
        title = "Sample Film",
        rating = 4.5f,
        description = "This is a sample film description."
    )

    //FilmDetailsScreen(film = sampleFilm)
}
