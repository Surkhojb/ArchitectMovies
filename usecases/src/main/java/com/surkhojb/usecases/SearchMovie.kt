package com.surkhojb.usecases

import com.surkhojb.data.repositories.MoviesRepository
import com.surkhojb.domain.Movie

class SearchMovie(private val moviesRepository: MoviesRepository) {

    suspend fun invoke(query: String): List<Movie> = moviesRepository.searchMovies(query)
}