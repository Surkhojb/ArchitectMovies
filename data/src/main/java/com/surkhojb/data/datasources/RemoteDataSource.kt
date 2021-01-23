package com.surkhojb.data.datasources

import com.surkhojb.domain.Cast
import com.surkhojb.domain.Movie

interface RemoteDataSource {
    suspend fun getTopRatedMovies(region: String,page: Int = 1): List<Movie>
    suspend fun getMovieCast(movieId: Int): List<Cast>
    suspend fun getNewestMovies(page: Int = 1): List<Movie>
    suspend fun searchMovie(query: String): List<Movie>
}