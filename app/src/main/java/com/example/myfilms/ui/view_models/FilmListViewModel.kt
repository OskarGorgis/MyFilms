package com.example.myfilms.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfilms.data.FilmEntity
import com.example.myfilms.data.FilmRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FilmListViewModel(private val filmRepository: FilmRepository) : ViewModel() {

    var filmListUiState: StateFlow<FilmListUiState> =
        filmRepository.getAllFilmsByIdStream().map { FilmListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILIS),
                initialValue = FilmListUiState()
            )

    fun deleteFilm(film: FilmEntity) = filmRepository.deleteFilm(film)

    companion object {
        private const val TIMEOUT_MILIS = 5_000L
    }

}

data class FilmListUiState(val filmList: List<FilmEntity> = listOf())