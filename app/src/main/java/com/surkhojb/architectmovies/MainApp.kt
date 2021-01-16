package com.surkhojb.architectmovies

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.datastore.preferences.createDataStore
import com.surkhojb.architectmovies.data.local.MoviesDatabase
import com.surkhojb.architectmovies.data.local.DataStoreSource
import com.surkhojb.architectmovies.di.DaggerMainAppComponent
import com.surkhojb.architectmovies.di.MainAppComponent

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()

        component = DaggerMainAppComponent
            .factory()
            .create(this)
    }

    companion object{
        lateinit var component: MainAppComponent
            private set
    }
}