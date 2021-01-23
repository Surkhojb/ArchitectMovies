package com.surkhojb.data.repositories

import com.nhaarman.mockitokotlin2.*
import com.surkhojb.data.datasources.LocalDataSource
import com.surkhojb.data.datasources.PreferencesDataSource
import com.surkhojb.data.datasources.RemoteDataSource
import com.surkhojb.domain.Cast
import com.surkhojb.domain.Movie
import com.surkhojb.domain.MovieCast
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesRepositoryTest {
    @Mock
    lateinit var localDataSource: LocalDataSource

    @Mock
    lateinit var preferencesDataSource: PreferencesDataSource

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    @Mock
    lateinit var regionRepository: RegionRepository

    lateinit var repository: MoviesRepository

    @Before
    fun setUp() {
        repository = MoviesRepository(localDataSource,
            preferencesDataSource,
            remoteDataSource,
            regionRepository)
    }

    @Test
    fun `getTopRatedMovies get movies from db if is not empty`(){
        runBlocking {

            val localMovies = listOf(mockedMovie.copy(1))
            whenever(localDataSource.areMoviesCached("top_rated")).thenReturn(true)
            whenever(localDataSource.getMoviesByType("top_rated")).thenReturn(localMovies)

            val moviesFromRepository = repository.getTopRatedMovies(false)
            assertEquals(localMovies,moviesFromRepository)
        }

    }

    @Test
    fun `getTopRatedMovies saves remote movies into db`(){
        runBlocking {
            val remoteMovie = listOf(mockedMovie.copy(2))
            whenever(localDataSource.areMoviesCached("top_rated")).thenReturn(false)
            whenever(remoteDataSource.getTopRatedMovies(regionRepository.findLastRegion(),1)).thenReturn(remoteMovie)

            repository.getTopRatedMovies(true)

            verify(localDataSource).cacheMovies(movies = remoteMovie)
        }

    }

    @Test
    fun `getMovieCast should call remotedatasource when cast is null then has to execute get getMovieById and updateMovie`(){
        runBlocking {
            val movies = listOf(mockedMovie.copy(3))
            val movie = movies[0]
            whenever(localDataSource.getMovieCast(3)).thenReturn(emptyList())
            whenever(localDataSource.getMovieById(3)).thenReturn(movie)

            repository.getMovieCast(3)

            verify(remoteDataSource).getMovieCast(3)
            verify(localDataSource).getMovieById(3)
            verify(localDataSource).updateMovie(movie)
        }

    }

    @Test
    fun `getMovieCast should call localdatasource when cast is not-null`(){
        runBlocking {
            val movie= mockedMovie.copy(id = 4,cast = MovieCast(listOf(mockedCast)))

            whenever(localDataSource.getMovieCast(4)).thenReturn(movie.cast?.cast)

            repository.getMovieCast(4)

            /* times = number of invocations
            In this case we call getMovieCast twice:
            - once to check cast is not null or empty
            - second time to return cast from db
            */
            verify(localDataSource, times(2)).getMovieCast(4)
        }

    }

    @Test
    fun `findById calls local db`(){
        runBlocking {
            val movie = mockedMovie.copy(5)
            whenever(localDataSource.getMovieById(5)).thenReturn(movie)

            val resultMovie = repository.getMovieById(5)

            assertEquals(movie,resultMovie)

        }
    }

    @Test
    fun `saveMovieAsFavorite call updateMovie local db`(){
        runBlocking {
            val movie = mockedMovie.copy(6)

            repository.saveMovieAsFavorite(movie)

            verify(localDataSource).updateMovie(movie)

        }
    }

    @Test
    fun `getNewestMovies gets from db if is not empty`(){
        runBlocking {

            val localMovies = listOf(mockedMovie.copy(7))
            whenever(localDataSource.areMoviesCached("newest")).thenReturn(true)
            whenever(localDataSource.getMoviesByType("newest")).thenReturn(localMovies)

            val moviesFromRepository = repository.getNewestMovies(false)
            assertEquals(localMovies,moviesFromRepository)
        }

    }

    @Test
    fun `getNewestMovies saves remote movies into db`(){
        runBlocking {
            val remoteMovie = listOf(mockedMovie.copy(8))
            whenever(localDataSource.areMoviesCached("newest")).thenReturn(false)
            whenever(remoteDataSource.getNewestMovies(1)).thenReturn(remoteMovie)

            repository.getNewestMovies(false)

            verify(localDataSource).cacheMovies("newest",movies = remoteMovie)
        }

    }

    @Test
    fun `getFavorites should return a non-empty list after saveMovieAsFavorite has been called`(){
        runBlocking {
            val movieToSave = mockedMovie.copy(9,favorite = true)
            repository.saveMovieAsFavorite(movieToSave)

            whenever(localDataSource.getFavorites()).thenReturn(listOf(movieToSave))

            val favorites = repository.getFavorites()
            assertEquals(favorites, listOf(movieToSave))
        }
    }

    @Test
    fun `searchMovies should call remoteDatasoruce, clean the last searchead movies in db and save the last movies into db`(){
        runBlocking {
            val searchs = listOf(mockedMovie.copy(10))
            whenever(remoteDataSource.searchMovie("")).thenReturn(searchs)

            repository.searchMovies("")

            verify(remoteDataSource).searchMovie("")
            verify(localDataSource).removeLastSearch()
            verify(localDataSource).cacheMovies("search",searchs)
        }
    }

    @Test
    fun `getLastSearchs should return the last 3 words saved from localdatasource`(){
        runBlocking {
            val words = listOf("word1","word2","word3")

            whenever(localDataSource.getWordsSearched()).thenReturn(words)

            repository.saveLastSearchh("word1")
            repository.saveLastSearchh("word2")
            repository.saveLastSearchh("word3")

            val lastSearchs = repository.getLastSearchs()

            verify(localDataSource).getWordsSearched()

            assertEquals(lastSearchs,words)
        }
    }

    @After
    fun tearDown() {
    }
}

val mockedMovie = Movie(
    0,
    "Title",
    "Overview",
    "01/01/2025",
    "",
    "",
    "EN",
    "Title",
    5.0,
    5.1,
    60,
    false,
    MovieCast(emptyList())
)

val mockedCast = Cast(
    true,
    "Character",
    "1",
    1,
    1,
    "deparmetn",
    "name",
    1,
    "originalName",
    10.0,
    ""
)