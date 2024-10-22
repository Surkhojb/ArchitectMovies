package com.surkhojb.data.repositories

import com.surkhojb.data.datasources.LocalDataSource
import com.surkhojb.data.datasources.PreferencesDataSource
import com.surkhojb.data.datasources.RemoteDataSource
import com.surkhojb.domain.Cast
import com.surkhojb.domain.Movie


private const val TOP_RATED = "top_rated"
private const val NEWEST_TYPE = "newest"
private const val SEARCH = "search"

class MoviesRepository(private val localDataSource: LocalDataSource,
                       private val preferencesDataSource: PreferencesDataSource,
                       private val remoteDataSource: RemoteDataSource,
                       private val regionRepository: RegionRepository) {

    suspend fun getTopRatedMovies(loadingMore: Boolean): List<Movie>{
        if(!localDataSource.areMoviesCached() || loadingMore ){
            val pageToLoad = preferencesDataSource.pageToLoad(TOP_RATED) ?: 1

            val movies = remoteDataSource.getTopRatedMovies(regionRepository.findLastRegion(),pageToLoad)

            localDataSource.cacheMovies(TOP_RATED,movies).also {
                preferencesDataSource.updatePage(TOP_RATED)
                return movies
            }
        }

        return localDataSource.getMoviesByType()
    }

    suspend fun getMovieCast(movieId: Int): List<Cast>{
        if(localDataSource.getMovieCast(movieId).isNullOrEmpty()){
            val cast = remoteDataSource.getMovieCast(movieId)
            localDataSource.getMovieById(movieId)?.apply {
                this.cast?.cast = cast ?: emptyList()
                localDataSource.updateMovie(this)
            }
        }

        return localDataSource.getMovieCast(movieId)
    }

   suspend fun getMovieById(movieId: Int): Movie {
        return localDataSource.getMovieById(movieId)
    }

    suspend fun saveMovieAsFavorite(movie: Movie): Any {
        return localDataSource.updateMovie(movie)
    }

    suspend fun getNewestMovies(loadingMore: Boolean): List<Movie>{
        if(!localDataSource.areMoviesCached(NEWEST_TYPE) || loadingMore ) {
            val pageToLoad = preferencesDataSource.pageToLoad(NEWEST_TYPE) ?: 1

            val movies = remoteDataSource.getNewestMovies(pageToLoad)

            localDataSource.cacheMovies(NEWEST_TYPE, movies).also {
                preferencesDataSource.updatePage(NEWEST_TYPE)
                return movies
            }
        }

        return localDataSource.getMoviesByType(NEWEST_TYPE)
    }

    suspend fun getFavorites(): List<Movie> {
        return localDataSource.getFavorites()
    }

    suspend fun searchMovies(query: String): List<Movie>{
        return remoteDataSource.searchMovie(query).also {movies ->
            localDataSource.removeLastSearch()
            localDataSource.cacheMovies(SEARCH,movies)
        }

    }

    suspend fun loadLastSearch(): List<Movie>{
        return localDataSource.getMoviesByType(SEARCH)
    }

    suspend fun getLastSearchs(): List<String>{
        return localDataSource.getWordsSearched().toList()
    }

    suspend fun saveLastSearchh(query: String): Any {
        return localDataSource.updateWordsSearched(query)
    }
}