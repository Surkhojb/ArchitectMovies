package com.surkhojb.architectmovies.ui.main

import com.surkhojb.architectmovies.dataModule
import com.surkhojb.architectmovies.ui.main.top_rated.mockedCast
import com.surkhojb.architectmovies.ui.main.top_rated.mockedMovie
import com.surkhojb.data.datasources.*
import com.surkhojb.domain.Cast
import com.surkhojb.domain.Movie
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun initMock(vararg modules: Module){
    startKoin{
        modules(listOf(appModuleTest, dataModule) + modules)
    }
}

var fakeMovies = listOf(mockedMovie.copy(id = 1), mockedMovie.copy(id = 2))
var fakeCast = listOf(mockedCast.copy(id = 1))

private val appModuleTest = module {
    single<LocalDataSource> { FakeLocalDataSource() }
    single<PreferencesDataSource> { FakePreferencesDataSource() }
    single<RemoteDataSource> { FakeRemoteDataSource() }
    single<LocationDataSource> { FakeLocationDataSource() }
    single<PermissionChecker> { FakePermissionChecker() }
    single { Dispatchers.Unconfined }
}

class FakeLocalDataSource: LocalDataSource {
    var localMovies: List<Movie> = emptyList()
    var wordsSearched: ArrayList<String> = arrayListOf()

    override suspend fun areMoviesCached(type: String): Boolean = localMovies.size > 0

    override suspend fun cacheMovies(type: String, movies: List<Movie>) { localMovies = movies }

    override suspend fun getMoviesByType(type: String): List<Movie> = localMovies

    override suspend fun getFavorites(): List<Movie> = localMovies.filter { it.favorite }

    override suspend fun getMovieCast(movieId: Int): List<Cast> = fakeCast

    override suspend fun getMovieById(movieId: Int): Movie = localMovies.first { it.id == movieId }

    override suspend fun updateMovie(movie: Movie) {
        localMovies = localMovies.filterNot { it.id == movie.id } + movie
    }

    override suspend fun getWordsSearched(): List<String> = wordsSearched.takeLast(3)

    override suspend fun updateWordsSearched(query: String): Boolean {
        wordsSearched.add(query)
        return true
    }

    override suspend fun removeLastSearch() { }

}

class FakePreferencesDataSource(): PreferencesDataSource{
    override suspend fun pageToLoad(type: String): Int? = 1

    override suspend fun updatePage(type: String) {}
}

class FakeRemoteDataSource: RemoteDataSource {

    var movies = fakeMovies

    override suspend fun getTopRatedMovies(region: String, page: Int): List<Movie> = movies

    override suspend fun getMovieCast(movieId: Int): List<Cast>? = fakeCast

    override suspend fun getNewestMovies(page: Int): List<Movie> = movies

    override suspend fun searchMovie(query: String): List<Movie> = movies

}

class FakeLocationDataSource: LocationDataSource {
    override suspend fun findLastLocation(): String? = "US"
}

class FakePermissionChecker: PermissionChecker {
    override suspend fun check(permissions: List<PermissionChecker.Permission>): Boolean  =  true

}