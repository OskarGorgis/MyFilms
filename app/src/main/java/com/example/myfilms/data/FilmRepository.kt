package com.example.myfilms.data

import kotlinx.coroutines.flow.Flow

class FilmRepository(private val filmDao: FilmDao) {

    fun getAllFilmsByIdStream(): Flow<List<FilmEntity>> = filmDao.getFilmsById()

    fun getAllFilmsByRatingStream(): Flow<List<FilmEntity>> = filmDao.getFilmsByRating()

    fun getFilmStream(id: Int): Flow<FilmEntity?> = filmDao.getItem(id)

    fun deleteFilm(film: FilmEntity) = filmDao.delete(film)

    fun updateFilm(film: FilmEntity) = filmDao.update(film)

    fun insertFilm(film: FilmEntity) = filmDao.insert(film)
}