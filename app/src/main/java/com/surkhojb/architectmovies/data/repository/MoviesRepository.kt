package com.surkhojb.architectmovies.data.repository

import com.surkhojb.architectmovies.MainApp
import com.surkhojb.architectmovies.R
import com.surkhojb.architectmovies.data.local.model.Cast
import com.surkhojb.architectmovies.data.mapper.mapToDbCast
import com.surkhojb.architectmovies.data.mapper.mapToDbMovie
import com.surkhojb.architectmovies.data.remote.MovieDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.surkhojb.architectmovies.data.local.model.Movie as DbMovie

class MoviesRepository {
    private val apiKey = MainApp.getContext().getString(R.string.api_key)
    private val locationRepository = LocationRepository()
    private val movieDb = MainApp.getDb()

    suspend fun findTopRatedMovies(): List<DbMovie> = withContext(Dispatchers.IO) {
        if(dbIsEmpty()){
            val movies = MovieDb.service.
            getTopRated(
                apiKey,
                locationRepository.findLastRegion())

           persistMovies(movies.movies.map { it.mapToDbMovie() })
        }

        getAllMovies()
    }

    suspend fun loadCast(movieId: Int): List<Cast>? = withContext(Dispatchers.IO) {
        if(getCast(movieId).isNullOrEmpty()){
            val cast = MovieDb.service.getCast(movieId, apiKey)
            val movie = getMovieById(movieId)
            movie.cast?.cast = cast.cast.map {it.mapToDbCast()}

            updateMovie(movie)

            getCast(movieId)
        }else {
            getCast(movieId)
        }


    }

    suspend fun getMovieById(movieId: Int): DbMovie = withContext(Dispatchers.IO){
        movieDb.moviesDao().getMovieById(movieId)
    }

    suspend fun saveAsFavorite(movie: DbMovie) = withContext( Dispatchers.IO) {
        updateMovie(movie)
    }

    private suspend fun dbIsEmpty(): Boolean = withContext( Dispatchers.IO) {
        movieDb.moviesDao().count() <= 0
    }

    private suspend fun persistMovies(movies: List<DbMovie>) = withContext( Dispatchers.IO) {
        movieDb.moviesDao().insertMovies(movies)
    }

    private suspend fun getAllMovies() = withContext( Dispatchers.IO) {
        movieDb.moviesDao().getAll()
    }

    private suspend fun getCast(movieId: Int): List<Cast>? = withContext( Dispatchers.IO) {
        movieDb.moviesDao().getMovieCast(movieId)?.cast
    }

    private suspend fun updateMovie(movie: DbMovie) = withContext(Dispatchers.IO){
        movieDb.moviesDao().updateMovie(movie)
    }

}