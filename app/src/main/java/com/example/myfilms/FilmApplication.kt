package com.example.myfilms

import android.app.Application
import com.example.myfilms.data.AppContainer
import com.example.myfilms.data.AppDataContainer

class FilmApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}