package com.surkhojb.usecases

import com.surkhojb.data.repositories.MoviesRepository

class GetTopRatedMovies(private val moviesRepository: MoviesRepository, val apiKey: String) {

    suspend fun invoke(loadMore: Boolean = false) = moviesRepository.getTopRatedMovies(loadMore)
}