package com.example.myfilms.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.myfilms.R
import com.example.myfilms.data.FilmEntity
import com.example.myfilms.ui.view_models.AppViewModelProvider
import com.example.myfilms.ui.view_models.FilmListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmListScreen(
    navigateToItemCreate: () -> Unit,
    navigateToItemDetails: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FilmListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val filmListUiState by viewModel.filmListUiState.collectAsState()

    // Wyskakujące okienko
    val showDialog = remember { mutableStateOf(false) }
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
            },
            title = { Text("Confirm Deletion") },
            text = { Text("Do you want to delete this item?") },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                        //viewModel.deleteFilm(film)
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                    }
                ) {
                    Text("No")
                }
            }
        )
    }
    // Wyskakujące okienko

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemCreate,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_button)
                )
            }
        }
    ) {innerPadding ->
        ListBody(
            filmList = filmListUiState.filmList,
            onItemClickEdit = navigateToItemDetails,
            onItemLongPress = { viewModel.deleteFilm(it) },
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        )

    }

}

@Composable
fun ListBody(filmList: List<FilmEntity>, onItemLongPress: (FilmEntity) -> Unit,
             onItemClickEdit: (Int) -> Unit, modifier: Modifier) {
    if (filmList.isEmpty()) {
        Text(text = "There is no films added!")
    } else {
        Box(modifier = modifier) {
            LazyColumn {
                items(items = filmList, key = { it.id }) {film ->
                    FilmCard(film = film, onItemClickEdit = onItemClickEdit,
                        onItemLongPress = onItemLongPress, modifier = Modifier.padding(8.dp))
                }
            }
        }

    }


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilmCard(film: FilmEntity, onItemClickEdit: (Int) -> Unit, onItemLongPress: (FilmEntity) -> Unit,
             modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
            .combinedClickable(
                onClick = { onItemClickEdit(film.id) },
                onLongClick = { onItemLongPress(film) }
            )
            ,
        color = Color.Gray,
        shape = MaterialTheme.shapes.medium
    ) {
        Card(modifier = modifier) {
            Column {
                Image(
                    painter = rememberImagePainter(film.imageResource),
                    contentDescription = film.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(194.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = film.title,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
private fun FilmCardPreview() {
    //FilmCard(FilmEntity(R.drawable.basic_film_icon_foreground, "Bee movie", 5.0f, "Best fim i have ever seen"))
}

@Preview
@Composable
private fun FilmListPreview() {

}