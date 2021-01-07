package com.surkhojb.usecases

import com.surkhojb.data.MoviesRepository
import com.surkhojb.domain.Movie

class SaveMovieAsFavorite(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(movie: Movie) = moviesRepository.saveMovieAsFavorite(movie)
}