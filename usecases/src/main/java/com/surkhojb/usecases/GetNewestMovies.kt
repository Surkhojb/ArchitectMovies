package com.surkhojb.usecases

import com.surkhojb.data.repositories.MoviesRepository
import com.surkhojb.domain.Movie

class GetNewestMovies(private val moviesRepository: MoviesRepository) {

    suspend fun invoke(): List<Movie> = moviesRepository.getNewestMovies()
}