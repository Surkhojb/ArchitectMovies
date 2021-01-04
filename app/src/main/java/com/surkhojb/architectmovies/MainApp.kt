package com.surkhojb.architectmovies

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.surkhojb.architectmovies.data.local.MoviesDatabase

class MainApp : Application() {


    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        movieDb = Room.databaseBuilder(this,MoviesDatabase::class.java,"movies")
            .build()
    }

    companion object{
        private lateinit var context: Context
        private lateinit var movieDb: MoviesDatabase

        fun getContext() = context
        fun getDb() = movieDb
    }
}