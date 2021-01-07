package com.surkhojb.architectmovies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.surkhojb.architectmovies.data.local.room.model.CastTypeConverter
import com.surkhojb.architectmovies.data.local.room.model.Movie

@Database(
    entities = [Movie::class],
    version = 1
)
@TypeConverters(CastTypeConverter::class)
abstract class MoviesDatabase: RoomDatabase() {

    abstract fun moviesDao(): MovieDao
}