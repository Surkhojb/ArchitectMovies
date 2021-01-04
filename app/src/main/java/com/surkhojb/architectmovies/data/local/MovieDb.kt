package com.surkhojb.architectmovies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.surkhojb.architectmovies.data.local.model.MovieDb

@Database(
    entities = [MovieDb::class],
    version = 1
)
abstract class LocalDb: RoomDatabase() {

    abstract fun moviesDao(): LocalDao
}