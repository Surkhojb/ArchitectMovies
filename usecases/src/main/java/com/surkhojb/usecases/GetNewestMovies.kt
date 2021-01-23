package com.surkhojb.usecases

import com.surkhojb.data.repositories.MoviesRepository
import com.surkhojb.domain.Movie

class GetNewestMovies(private val moviesRepository: MoviesRepository) {

    suspend fun invoke(loadMore: Boolean = false): List<Movie> = moviesRepository.getNewestMovies(loadMore)
}