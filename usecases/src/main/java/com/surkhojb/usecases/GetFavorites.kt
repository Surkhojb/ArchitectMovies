package com.surkhojb.usecases

import com.surkhojb.data.repositories.MoviesRepository
import com.surkhojb.domain.Movie

class GetFavorites(private val moviesRepository: MoviesRepository) {

    suspend fun invoke(): List<Movie> = moviesRepository.getFavorites()
}