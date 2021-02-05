package com.surkhojb.architectmovies.data.local

import com.surkhojb.architectmovies.data.local.room.model.MovieSearchs
import com.surkhojb.architectmovies.data.mapper.mapToDomainCast
import com.surkhojb.architectmovies.data.mapper.mapToDomainMovie
import com.surkhojb.architectmovies.data.mapper.toRoomMovie
import com.surkhojb.data.datasources.LocalDataSource
import com.surkhojb.domain.Cast
import com.surkhojb.domain.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomDataSource(private val moviesDao: MovieDao): LocalDataSource {

    override suspend fun areMoviesCached(type: String): Boolean = withContext(Dispatchers.IO) {
        moviesDao.count(type) > 0
    }

    override suspend fun cacheMovies(type: String, movies: List<Movie>) = withContext(Dispatchers.IO)  {
        val roomMovies = movies.map { it.toRoomMovie(type) }
        moviesDao.insertMovies(roomMovies)
    }

    override suspend fun getMoviesByType(type: String): List<Movie> = withContext(Dispatchers.IO) {
        moviesDao.getAll().filter { it.movieType == type }.map { it.mapToDomainMovie() }
    }

    override suspend fun getFavorites(): List<Movie> = withContext(Dispatchers.IO){
        moviesDao.getAll().filter { it.favorite }.map { it.mapToDomainMovie() }
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

    override suspend fun getWordsSearched(): List<String> = withContext(Dispatchers.IO) {
        moviesDao.getMovieSearchs()?.wordsSearched?.takeLast(3) ?: arrayListOf()
    }

    override suspend fun updateWordsSearched(query: String): Boolean = withContext(Dispatchers.IO) {
        val movieSearchs = moviesDao.getMovieSearchs()


        if(movieSearchs == null){
            moviesDao.insertMovieSearchs(MovieSearchs().apply {
                wordsSearched.add(query)
            })
        }else if (movieSearchs.wordsSearched.contains(query) == false){
                movieSearchs.wordsSearched.add(query)
                moviesDao.updateMovieSearchs(movieSearchs)
                true
            }
        true
    }

    override suspend fun removeLastSearch() = withContext(Dispatchers.IO){
       moviesDao.removeLastSearch()
    }
}