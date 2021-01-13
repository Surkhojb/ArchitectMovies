package com.surkhojb.usecases

import com.surkhojb.data.repositories.MoviesRepository

class GetTopRatedMovies(private val moviesRepository: MoviesRepository) {

    suspend fun invoke(loadMore: Boolean = false) = moviesRepository.getTopRatedMovies(loadMore)
}