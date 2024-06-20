package com.example.myfilms.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.myfilms.R
import com.example.myfilms.model.FilmDetails
import com.example.myfilms.ui.elements.RatingStarBar
import com.example.myfilms.ui.navigation.NavigationDestination
import com.example.myfilms.ui.view_models.AppViewModelProvider
import com.example.myfilms.ui.view_models.FilmCreateViewModel
import com.example.myfilms.ui.view_models.FilmUiState
import kotlinx.coroutines.launch

object FilmCreateDestination : NavigationDestination {
    override val route: String = "Create"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmCreateScreen(
    navigateBack: () -> Unit,
    viewModel: FilmCreateViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold {
        innerPadding ->
        FilmCreateBody(
            filmUiState = viewModel.filmUiState,
            onFilmValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveItem()
                    navigateBack()
                }
            },
            onBack = navigateBack,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmCreateBody(
    filmUiState: FilmUiState,
    onFilmValueChange: (FilmDetails) -> Unit,
    onSaveClick: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        val getContent = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                onFilmValueChange(filmUiState.filmDetails.copy(imageResource = it.toString()))
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.Gray)
                .clip(shape = MaterialTheme.shapes.medium)
                .clickable {
                    getContent.launch("image/*")
                },
            contentAlignment = Alignment.Center
        ) {
            if (filmUiState.filmDetails.imageResource != "") {
                Image(
                    painter = rememberImagePainter(filmUiState.filmDetails.imageResource), // Placeholder image
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape = MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Default.AddAPhoto,
                    contentDescription = "Add Photo",
                    modifier = Modifier.size(48.dp)
                )
            }
        }

        Column {
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = filmUiState.filmDetails.title,
                onValueChange = { onFilmValueChange(filmUiState.filmDetails.copy(title = it)) },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))


            Text(text = "Rating")
            RatingStarBar(
                currentRating = filmUiState.filmDetails.rating.toInt(),
                onRatingChanged = { onFilmValueChange(filmUiState.filmDetails.copy(rating = it.toFloat())) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = filmUiState.filmDetails.description,
                onValueChange = { onFilmValueChange(filmUiState.filmDetails.copy(description = it)) },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Button(
                    onClick = {
                        onBack()
                    }
                ) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Cancel")
                    Text(text = "Anuluj")
                }

                Button(
                    onClick = {
                        onSaveClick()
                    }
                ) {
                    Icon(imageVector = Icons.Default.Save, contentDescription = "Save")
                    Text(text = "Zapisz")
                }
            }
        }


    }
}