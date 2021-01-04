package com.surkhojb.architectmovies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import androidx.room.Update
import com.surkhojb.architectmovies.data.local.model.MovieDb

@Dao
interface LocalDao {

    @Query("SELECT * FROM movies")
    fun getAll(): List<MovieDb>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovieById(movieId: Int)

    @Query("SELECT COUNT(id) fROM movies")
    fun count(): Int

    @Insert(onConflict = IGNORE)
    fun insertMovies(movies: List<MovieDb>)

    @Update
    fun updateMovie(movie: MovieDb)

}