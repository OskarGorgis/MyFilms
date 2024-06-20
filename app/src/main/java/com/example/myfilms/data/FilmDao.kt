package com.example.myfilms.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDao {

    @Query("SELECT * FROM films_table ORDER BY id ASC")
    fun getFilmsById(): Flow<List<FilmEntity>>

    @Query("SELECT * FROM films_table ORDER BY rating, title ASC ")
    fun getFilmsByRating(): Flow<List<FilmEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(film: FilmEntity)

    @Query("SELECT * FROM films_table WHERE id = :id")
    fun getItem(id: Int): Flow<FilmEntity>

    @Update
    fun update(film: FilmEntity)

    @Delete
    fun delete(film: FilmEntity)


}