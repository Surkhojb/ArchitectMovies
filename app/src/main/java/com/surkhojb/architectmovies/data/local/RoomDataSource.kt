package com.surkhojb.architectmovies.data.local

import com.surkhojb.architectmovies.MainApp
import com.surkhojb.architectmovies.data.mapper.mapToDomainCast
import com.surkhojb.architectmovies.data.mapper.mapToDomainMovie
import com.surkhojb.architectmovies.data.mapper.toRoomMovie
import com.surkhojb.data.datasources.LocalDataSource
import com.surkhojb.domain.Cast
import com.surkhojb.domain.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomDataSource: LocalDataSource {
    private val moviesDao = MainApp.getDb().moviesDao()

    override suspend fun areMoviesCached(): Boolean = withContext(Dispatchers.IO) {
        moviesDao.count() > 0
    }

    override suspend fun cacheMovies(movies: List<Movie>) = withContext(Dispatchers.IO)  {
        val roomMovies = movies.map { it.toRoomMovie() }
        moviesDao.insertMovies(roomMovies)
    }

    override suspend fun getTopRatedMovies(): List<Movie>  = withContext(Dispatchers.IO) {
        moviesDao.getAll().map { it.mapToDomainMovie() }
    }

    override suspend fun getMovieCast(movieId: Int): List<Cast>  = withContext(Dispatchers.IO) {
        moviesDao.getMovieCast(movieId).cast.map { it.mapToDomainCast() }
    }

    override suspend fun getMovieById(movieId: Int): Movie  = withContext(Dispatchers.IO) {
        moviesDao.getMovieById(movieId).mapToDomainMovie()
    }

    override suspend fun updateMovie(movie: Movie) = withContext(Dispatchers.IO) {
        moviesDao.updateMovie(movie.toRoomMovie())
    }
}