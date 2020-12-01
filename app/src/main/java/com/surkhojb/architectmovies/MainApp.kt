package com.surkhojb.architectmovies

import android.app.Application
import android.content.Context

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object{
        private lateinit var context: Context
        fun getContext() = context
    }
}