package com.surkhojb.architectmovies.data.repository

import com.surkhojb.architectmovies.MainApp
import com.surkhojb.architectmovies.R
import com.surkhojb.architectmovies.data.local.model.MovieCast
import com.surkhojb.architectmovies.model.Cast as RemoteCast
import com.surkhojb.architectmovies.data.local.model.Movie as DbMovie
import com.surkhojb.architectmovies.data.local.model.Cast as Cast
import com.surkhojb.architectmovies.model.Movie as RemoteMovie
import com.surkhojb.architectmovies.data.remote.MovieDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    private suspend fun dbIsEmpty(): Boolean = withContext( Dispatchers.IO) {
        movieDb.moviesDao().count() <= 0
    }

    private suspend fun persistMovies(movies: List<DbMovie>) = withContext( Dispatchers.IO) {
        movieDb.moviesDao().insertMovies(movies)
    }

    private suspend fun getAllMovies() = withContext( Dispatchers.IO) {
        movieDb.moviesDao().getAll()
    }

    suspend fun getMovieById(movieId: Int): DbMovie = withContext(Dispatchers.IO){
        movieDb.moviesDao().getMovieById(movieId)
    }

    private suspend fun getCast(movieId: Int): List<Cast>? = withContext( Dispatchers.IO) {
        movieDb.moviesDao().getMovieCast(movieId)?.cast
    }

    private suspend fun updateMovie(movie: DbMovie) = withContext(Dispatchers.IO){
        movieDb.moviesDao().updateMovie(movie)
    }

    private fun RemoteMovie.mapToDbMovie() = DbMovie(
        id,
        title,
        overview,
        releaseDate,
        posterPath,
        backdropPath,
        originalLanguage,
        originalTitle,
        popularity,
        voteAverage,
        voteCount,
        false,
        MovieCast(emptyList())
    )

    private fun RemoteCast.mapToDbCast() = Cast(
        adult,
        character,
        credit_id,
        gender, id,
        known_for_department,
        name,
        order,
        original_name,
        popularity,
        profile_path
    )

}