package com.example.myfilms.model

import com.example.myfilms.data.FilmEntity

data class FilmDetails(
    val id: Int = 0,
    val imageResource: String = "",
    val title: String = "",
    val rating: Float = 0F,
    val description: String = ""
)

fun FilmDetails.toFilmEntity(): FilmEntity = FilmEntity(
    id = id,
    imageResource = imageResource,
    title = title,
    rating = rating,
    description = description
)

