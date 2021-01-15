package com.surkhojb.architectmovies.data.remote

import com.surkhojb.architectmovies.data.mapper.mapToDomainMovie
import com.surkhojb.architectmovies.data.mapper.mapToDomainCast
import com.surkhojb.architectmovies.data.remote.retrofit.MovieDb
import com.surkhojb.data.datasources.RemoteDataSource
import com.surkhojb.domain.Cast
import com.surkhojb.domain.Movie

class TMDBDataSource: RemoteDataSource {
    override suspend fun getTopRatedMovies(region: String,page: Int): List<Movie> {
        return MovieDb.service.getTopRated(region,page).movies.map { it.mapToDomainMovie() }
    }

    override suspend fun getMovieCast(movieId: Int): List<Cast> {
       return MovieDb.service.getCast(movieId).cast.map { it.mapToDomainCast() }
    }

    override suspend fun getNewestMovies(): List<Movie> {
        return MovieDb.service.getNewest().movies.map { it.mapToDomainMovie() }
    }

    override suspend fun searchMovie(query: String): List<Movie> {
        return MovieDb.service.searchMovie(query).movies
            .filter { it.posterPath != null }
            .map { it.mapToDomainMovie() }
    }
}