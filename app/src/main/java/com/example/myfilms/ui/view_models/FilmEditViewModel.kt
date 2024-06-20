package com.example.myfilms.ui.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfilms.data.FilmRepository
import com.example.myfilms.data.toFilmUiState
import com.example.myfilms.model.FilmDetails
import com.example.myfilms.model.toFilmEntity
import com.example.myfilms.ui.FilmEditDestination
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class FilmEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val filmRepository: FilmRepository
) : ViewModel() {
    var filmUiState by mutableStateOf(FilmUiState())
        private set

    private val filmId: Int = checkNotNull(savedStateHandle[FilmEditDestination.itemIdArg])

    init {
        viewModelScope.launch {
            filmUiState = filmRepository.getFilmStream(filmId)
                .filterNotNull()
                .first()
                .toFilmUiState(true)
        }
    }

    suspend fun updateFilm() {
        if (validateInputs(filmUiState.filmDetails)) {
            filmRepository.updateFilm(filmUiState.filmDetails.toFilmEntity())
        }
    }

    fun updateUiState(filmDetails: FilmDetails) {
        filmUiState = FilmUiState(filmDetails = filmDetails, isFilmValid = validateInputs(filmDetails))
    }

    private fun validateInputs(uiState: FilmDetails = filmUiState.filmDetails): Boolean {
        return with(uiState) {
            imageResource.isNotBlank() && title.isNotBlank() &&
                    description.isNotBlank() && !rating.isNaN()
        }
    }
}