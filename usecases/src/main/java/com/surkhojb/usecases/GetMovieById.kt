package com.surkhojb.usecases

import com.surkhojb.data.MoviesRepository
import com.surkhojb.domain.Movie

class GetMovieById(private val moviesRepository : MoviesRepository) {
    suspend fun invoke(movieId: Int) = moviesRepository.getMovieById(movieId)
}