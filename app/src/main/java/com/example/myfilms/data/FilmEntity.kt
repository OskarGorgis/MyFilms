package com.example.myfilms.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myfilms.model.FilmDetails
import com.example.myfilms.ui.view_models.FilmUiState

@Entity(tableName = "films_table")
class FilmEntity (
    id: Int?,
    imageResource: String,
    title: String,
    rating: Float,
    description: String
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var imageResource: String = imageResource
    var title: String = title
    var rating: Float = rating
    var description: String = description

    init {
        this.id = id ?: 0
    }
}

fun FilmEntity.toFilmDetails() : FilmDetails = FilmDetails(
    id = id,
    imageResource = imageResource,
    title = title,
    rating = rating,
    description = description
)

fun FilmEntity.toFilmUiState(isFilmValid: Boolean = false): FilmUiState = FilmUiState(
    filmDetails = this.toFilmDetails(),
    isFilmValid = isFilmValid
)