package com.surkhojb.architectmovies.data.repository

import com.surkhojb.architectmovies.MainApp
import com.surkhojb.architectmovies.R
import com.surkhojb.architectmovies.data.remote.MovieDb

class MoviesRepository {
    private val apiKey = MainApp.getContext().getString(R.string.api_key)
    private val locationRepository = LocationRepository()

    suspend fun findTopRatedMovies() = MovieDb.service.getTopRated(
        apiKey,
        locationRepository.findLastRegion()
    )

    suspend fun loadCast(movieId: Int) = MovieDb.service.getCast(movieId, apiKey)

}