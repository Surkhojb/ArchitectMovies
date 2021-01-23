package com.surkhojb.usecases

import com.surkhojb.data.repositories.MoviesRepository

class LastSearchs(val moviesRepository: MoviesRepository) {

    suspend fun get(): List<String> = moviesRepository.getLastSearchs()
}