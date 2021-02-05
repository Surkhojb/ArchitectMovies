package com.surkhojb.architectmovies.ui.main.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.surkhojb.architectmovies.ui.main.FakeLocalDataSource
import com.surkhojb.architectmovies.ui.main.FakeRemoteDataSource
import com.surkhojb.architectmovies.ui.main.fakeMovies
import com.surkhojb.architectmovies.ui.main.initMock
import com.surkhojb.architectmovies.ui.main.top_rated.mockedMovie
import com.surkhojb.data.datasources.LocalDataSource
import com.surkhojb.data.datasources.RemoteDataSource
import com.surkhojb.domain.Movie
import com.surkhojb.usecases.LastSearchs
import com.surkhojb.usecases.SearchMovie
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class SearchIntegrationTest: AutoCloseKoinTest() {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    lateinit var viewModel: SearchViewModel

    @Mock
    lateinit var observerMovies: Observer<List<Movie>>

    @Mock
    lateinit var observerSearchs: Observer<List<String>>

    @Before
    fun setUp(){
        val module = module {
            factory { SearchViewModel(get(),get(),get()) }
            factory { SearchMovie(get()) }
            factory { LastSearchs(get()) }
        }

        initMock(module)

        viewModel = get()
    }

    @Test
    fun `data is loaded from remote and movie name should contain query`() {
        runBlocking {
            val searchedMovies = listOf(mockedMovie.copy(id = 1,originalTitle = "Juno"),
                mockedMovie.copy(id = 2,originalTitle = "Juno2"),
                mockedMovie.copy(id = 1,originalTitle = "Juno3"))

            val remoteDataSource = get<RemoteDataSource>() as FakeRemoteDataSource
            remoteDataSource.movies = searchedMovies

            viewModel.movies.observeForever(observerMovies)

            viewModel.searchMovie("Juno")

            verify(observerMovies).onChanged(viewModel.movies.value)

            val liveDataMovies = viewModel.movies.value

            assertEquals(searchedMovies,liveDataMovies)
        }
    }

    @Test
    fun `after search many times, last searchs should be the last 3 searched words`() {
        runBlocking {
            val searchedWords = listOf("Juno","America","Indiana")

            viewModel.searchMovie("Juno")
            viewModel.searchMovie("America")
            viewModel.searchMovie("Indiana")

            viewModel.searchs.observeForever(observerSearchs)

            verify(observerSearchs).onChanged(viewModel.searchs.value)

            val liveDataSearchs = viewModel.searchs.value

            assertEquals(searchedWords,liveDataSearchs)
        }
    }

    @Test
    fun `default movies shouldb be the last movies searched`() {
        runBlocking {
            val lastMovies = fakeMovies

            val localDataSource = get<LocalDataSource>() as FakeLocalDataSource
            localDataSource.localMovies = fakeMovies

            viewModel.movies.observeForever(observerMovies)

            viewModel.loadLastMoviesSearched()

            verify(observerMovies).onChanged(viewModel.movies.value)

            val liveDataMovies = viewModel.movies.value

            assertEquals(lastMovies,liveDataMovies)
        }
    }
}