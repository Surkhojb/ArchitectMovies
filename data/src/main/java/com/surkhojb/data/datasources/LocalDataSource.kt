package com.surkhojb.data.datasources

import com.surkhojb.domain.Cast
import com.surkhojb.domain.Movie

interface LocalDataSource{
    suspend fun areMoviesCached(type:String = "top_rated"): Boolean
    suspend fun cacheMovies(type: String = "top_rated",movies: List<Movie>)
    suspend fun getMoviesByType(type: String = "top_rated"): List<Movie>
    suspend fun getFavorites(): List<Movie>
    suspend fun getMovieCast(movieId: Int): List<Cast>
    suspend fun getMovieById(movieId: Int): Movie
    suspend fun updateMovie(movie: Movie)
    suspend fun getWordsSearched(): ArrayList<String>
    suspend fun updateWordsSearched(query: String): Boolean
}