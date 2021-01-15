package com.surkhojb.architectmovies

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.datastore.preferences.createDataStore
import com.surkhojb.architectmovies.data.local.MoviesDatabase
import com.surkhojb.architectmovies.data.local.DataStoreSource

class MainApp : Application() {


    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        movieDb = Room.databaseBuilder(this,MoviesDatabase::class.java,"movies")
            .build()

        val dataStore = applicationContext.createDataStore("settings")

        dataStoreSource = DataStoreSource(dataStore)
    }

    companion object{
        private lateinit var context: Context
        private lateinit var movieDb: MoviesDatabase
        private lateinit var dataStoreSource: DataStoreSource

        fun getContext() = context
        fun getDb() = movieDb
        fun getDataStoreSource() = dataStoreSource
    }
}