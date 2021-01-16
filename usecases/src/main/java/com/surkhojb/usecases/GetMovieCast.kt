package com.surkhojb.usecases

import com.surkhojb.data.repositories.MoviesRepository

class GetMovieCast(private val moviesRepository: MoviesRepository) {

    suspend fun invoke(movieId: Int) = moviesRepository.getMovieCast(movieId)
}