package com.surkhojb.architectmovies.data.local.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "movies")
data class Movie(
        @PrimaryKey(autoGenerate = false)
        val id: Int,
        val title: String,
        val overview: String,
        val releaseDate: String,
        val posterPath: String,
        val backdropPath: String,
        val originalLanguage: String,
        val originalTitle: String,
        val popularity: Double,
        val voteAverage: Double,
        val voteCount: Int,
        val favorite: Boolean,
        @TypeConverters(CastTypeConverter::class)
        @ColumnInfo(name = "cast")
        var cast: MovieCast?
)