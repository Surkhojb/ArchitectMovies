package com.surkhojb.architectmovies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.surkhojb.architectmovies.data.local.room.model.ArrayListConverter
import com.surkhojb.architectmovies.data.local.room.model.CastTypeConverter
import com.surkhojb.architectmovies.data.local.room.model.Movie
import com.surkhojb.architectmovies.data.local.room.model.MovieSearchs

@Database(
    entities = [Movie::class, MovieSearchs::class],
    version = 1
)
@TypeConverters(CastTypeConverter::class, ArrayListConverter::class)
abstract class MoviesDatabase: RoomDatabase() {

    abstract fun moviesDao(): MovieDao
}