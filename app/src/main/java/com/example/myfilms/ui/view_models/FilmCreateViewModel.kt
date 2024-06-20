package com.example.myfilms.ui.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myfilms.data.FilmRepository
import com.example.myfilms.model.FilmDetails
import com.example.myfilms.model.toFilmEntity

class FilmCreateViewModel(private val filmRepository: FilmRepository) : ViewModel() {

    var filmUiState by mutableStateOf(FilmUiState())
        private set

    fun updateUiState(filmDetails: FilmDetails) {
        filmUiState =
            FilmUiState(filmDetails = filmDetails, isFilmValid = validateFilm(filmDetails))
    }

    suspend fun saveItem() {
        if (validateFilm()) {
            filmRepository.insertFilm(filmUiState.filmDetails.toFilmEntity())
        }
    }

    private fun validateFilm(uiState: FilmDetails = filmUiState.filmDetails): Boolean {
        return with(uiState) {
            description.isNotBlank() && title.isNotBlank() &&
                    !rating.isNaN() && imageResource.isNotBlank()
        }
    }

}

data class FilmUiState(
    val filmDetails: FilmDetails = FilmDetails(),
    val isFilmValid: Boolean = false
)
