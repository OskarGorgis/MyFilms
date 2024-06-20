package com.example.myfilms.ui.view_models

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfilms.data.FilmRepository
import com.example.myfilms.data.toFilmDetails
import com.example.myfilms.model.FilmDetails
import com.example.myfilms.model.toFilmEntity
import com.example.myfilms.ui.FilmDetailsDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FilmDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val filmRepository: FilmRepository
) : ViewModel() {

    private val filmId: Int = checkNotNull(savedStateHandle[FilmDetailsDestination.itemIdArg])

    val uiState: StateFlow<FilmDetailsUiState> =
        filmRepository.getFilmStream(filmId)
            .filterNotNull()
            .map {
                FilmDetailsUiState(filmDetails = it.toFilmDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = FilmDetailsUiState()
            )

    suspend fun deleteItem() {
        filmRepository.deleteFilm(uiState.value.filmDetails.toFilmEntity())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class FilmDetailsUiState(
    val filmDetails: FilmDetails = FilmDetails()
)

