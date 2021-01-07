package com.surkhojb.data

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
}


interface LocalDataSource{
    suspend fun areMoviesCached(): Boolean
    suspend fun cacheMovies(movies: List<Movie>)
    suspend fun getTopRatedMovies(): List<Movie>
    suspend fun getMovieCast(movieId: Int): List<Cast>
    suspend fun getMovieById(movieId: Int): Movie
    suspend fun updateMovie(movie: Movie)
}

interface RemoteDataSource {
    suspend fun getTopRatedMovies(region: String,page: Int = 1): List<Movie>
    suspend fun getMovieCast(movieId: Int): List<Cast>
}

interface PreferencesDataSource {
    suspend fun pageToLoad(): Int
    suspend fun updatePage()
}