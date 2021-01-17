package com.surkhojb.usecases

import com.surkhojb.data.repositories.MoviesRepository

class LastSearchs(val moviesRepository: MoviesRepository) {

    suspend fun put(query: String) = moviesRepository.saveLastSearchh(query)

    suspend fun get(): List<String> = moviesRepository.getLastSearchs()
}