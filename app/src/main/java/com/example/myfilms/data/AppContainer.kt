package com.example.myfilms.data

import android.content.Context

interface AppContainer {
    val filmRepository: FilmRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val filmRepository: FilmRepository by lazy {
        FilmRepository(AppDatabase.getDatabase(context).filmDao())
    }
}