package com.surkhojb.data.repositories

import com.surkhojb.data.datasources.LocalDataSource
import com.surkhojb.data.datasources.PreferencesDataSource
import com.surkhojb.data.datasources.RemoteDataSource
import com.surkhojb.domain.Cast
import com.surkhojb.domain.Movie

class MoviesRepository(private val localDataSource: LocalDataSource,
                       private val preferencesDataSource: PreferencesDataSource,
                       private val remoteDataSource: RemoteDataSource,
                       private val regionRepository: RegionRepository) {

    suspend fun getTopRatedMovies(loadingMore: Boolean): List<Movie>{
        if(!localDataSource.areMoviesCached() || loadingMore ){
            var pageToLoad = preferencesDataSource.pageToLoad()

            val movies = remoteDataSource.getTopRatedMovies(regionRepository.findLastRegion(),pageToLoad)

            localDataSource.cacheMovies(movies).also {
                preferencesDataSource.updatePage()
                return movies
            }
        }

        return localDataSource.getTopRatedMovies()
    }

    suspend fun getMovieCast(movieId: Int): List<Cast>{
        if(localDataSource.getMovieCast(movieId).isNullOrEmpty()){
            val cast = remoteDataSource.getMovieCast(movieId)
            val movieToUpdate = localDataSource.getMovieById(movieId)
            movieToUpdate.cast?.cast = cast

            localDataSource.updateMovie(movieToUpdate)

            return cast
        }

        return localDataSource.getMovieCast(movieId)
    }

   suspend fun getMovieById(movieId: Int): Movie {
        return localDataSource.getMovieById(movieId)
    }

    suspend fun saveMovieAsFavorite(movie: Movie): Any {
        return localDataSource.updateMovie(movie)
    }

    suspend fun getNewestMovies(): List<Movie>{
        val movies = remoteDataSource.getNewestMovies()
        localDataSource.cacheMovies(movies).also {
            preferencesDataSource.updatePage()
            return movies
        }
    }

    suspend fun getFavorites(): List<Movie> {
        return localDataSource.getFavorites()
    }
}