package com.surkhojb.architectmovies.data.remote

import com.surkhojb.architectmovies.data.mapper.mapToDomainMovie
import com.surkhojb.architectmovies.data.mapper.mapToDomainCast
import com.surkhojb.architectmovies.data.remote.retrofit.MovieDb
import com.surkhojb.data.datasources.RemoteDataSource
import com.surkhojb.domain.Cast
import com.surkhojb.domain.Movie

class TMDBDataSource(private val movieApi: MovieDb): RemoteDataSource {
    override suspend fun getTopRatedMovies(region: String,page: Int): List<Movie> {
        return movieApi.service.getTopRated(region,page).movies.map { it.mapToDomainMovie() }
    }

    override suspend fun getMovieCast(movieId: Int): List<Cast> {
       return movieApi.service.getCast(movieId)?.cast?.map { it.mapToDomainCast() } ?: emptyList()
    }

    override suspend fun getNewestMovies(page: Int): List<Movie> {
        return movieApi.service.getNewest(page).movies.map { it.mapToDomainMovie() }
    }

    override suspend fun searchMovie(query: String): List<Movie> {
        return movieApi.service.searchMovie(query).movies
            .filter { it.posterPath != null }
            .map { it.mapToDomainMovie() }
    }
}