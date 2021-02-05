package com.surkhojb.architectmovies

import android.app.Application

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}