package com.example.myfilms.ui.view_models

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myfilms.FilmApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            FilmEditViewModel(
                this.createSavedStateHandle(),
                filmApplication().container.filmRepository
            )
        }

        initializer {
            FilmCreateViewModel(filmApplication().container.filmRepository)
        }

        initializer {
            FilmDetailsViewModel(
                this.createSavedStateHandle(),
                filmApplication().container.filmRepository
            )
        }

        initializer {
            FilmListViewModel(filmApplication().container.filmRepository)
        }


    }
}

fun CreationExtras.filmApplication(): FilmApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as FilmApplication)