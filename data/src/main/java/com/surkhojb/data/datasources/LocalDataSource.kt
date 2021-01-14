package com.surkhojb.data.datasources

import com.surkhojb.domain.Cast
import com.surkhojb.domain.Movie

interface LocalDataSource{
    suspend fun areMoviesCached(): Boolean
    suspend fun cacheMovies(movies: List<Movie>)
    suspend fun getTopRatedMovies(): List<Movie>
    suspend fun getFavorites(): List<Movie>
    suspend fun getMovieCast(movieId: Int): List<Cast>
    suspend fun getMovieById(movieId: Int): Movie
    suspend fun updateMovie(movie: Movie)
}