package com.surkhojb.architectmovies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import androidx.room.Update
import com.surkhojb.architectmovies.data.local.room.model.*

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getAll(): List<Movie>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovieById(movieId: Int): Movie

    @Query("SELECT `cast` FROM movies WHERE id = :movieId")
    fun getMovieCast(movieId: Int): MovieCast

    @Query("SELECT COUNT(id) fROM movies WHERE movieType = :type")
    fun count(type: String): Int

    @Insert(onConflict = IGNORE)
    fun insertMovies(movies: List<Movie>)

    @Update
    fun updateMovie(movie: Movie)

    @Update
    fun updateMovieCast(movie: Movie)

    @Query("SELECT * FROM movies_searchs")
    fun getMovieSearchs(): MovieSearchs?

    @Insert(onConflict = IGNORE)
    fun insertMovieSearchs(movieSearchs: MovieSearchs)

    @Update
    fun updateMovieSearchs(movieSearchs: MovieSearchs)

    @Query("DELETE from movies WHERE movieType = 'search'")
    fun removeLastSearch()

}