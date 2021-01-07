package com.surkhojb.usecases

import com.surkhojb.data.repositories.MoviesRepository

class GetMovieById(private val moviesRepository : MoviesRepository) {
    suspend fun invoke(movieId: Int) = moviesRepository.getMovieById(movieId)
}