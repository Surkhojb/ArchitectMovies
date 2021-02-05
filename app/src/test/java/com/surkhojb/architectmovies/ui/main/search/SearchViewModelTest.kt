package com.surkhojb.architectmovies.ui.main.newest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.surkhojb.architectmovies.ui.common.Event
import com.surkhojb.architectmovies.ui.main.search.SearchViewModel
import com.surkhojb.architectmovies.ui.main.top_rated.mockedMovie
import com.surkhojb.domain.Movie
import com.surkhojb.usecases.LastSearchs
import com.surkhojb.usecases.SearchMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var searchMovie: SearchMovie

    @Mock
    lateinit var lastSearchs: LastSearchs

    @Mock
    lateinit var observerIndicator: Observer<Boolean>

    @Mock
    lateinit var observerMovies: Observer<List<Movie>>

    @Mock
    lateinit var observerSearchs: Observer<List<String>>

    @Mock
    lateinit var observerNavigate: Observer<Event<Movie>>

    lateinit var viewModel: SearchViewModel

    @Before
    fun setUp() {
        viewModel = SearchViewModel(Dispatchers.Unconfined, searchMovie,lastSearchs)
    }

    @Test
    fun `search movies, loading should change`() {
        runBlocking {
            val movies = listOf(mockedMovie.copy(id = 1))

            whenever(searchMovie.invoke("")).thenReturn(movies)

            viewModel.searchMovie("")

            viewModel.loading.observeForever(observerIndicator)

            verify(observerIndicator).onChanged(viewModel.loading.value)
        }
    }

    @Test
    fun `after search movies, movies should change`() {
        runBlocking {
            val movies = listOf(mockedMovie.copy(id = 1))

            whenever(searchMovie.invoke("")).thenReturn(movies)

            viewModel.searchMovie("")

            viewModel.movies.observeForever(observerMovies)

            verify(observerMovies).onChanged(viewModel.movies.value)
        }
    }


    @Test
    fun `after search movies, searchs value should change`() {
        runBlocking {
            val movies = listOf(mockedMovie.copy(id = 1))

            whenever(searchMovie.invoke("")).thenReturn(movies)

            viewModel.searchMovie("")

            viewModel.searchs.observeForever(observerSearchs)

            verify(observerSearchs).onChanged(viewModel.searchs.value)
        }
    }

    @Test
    fun `init viewModel should call load last movies searchend and invoke searchMovies initLoad`() {
        runBlocking {
            val movies = listOf(mockedMovie.copy(id = 1))

            whenever(searchMovie.initLoad()).thenReturn(movies)

            viewModel.loadLastMoviesSearched()

            verify(searchMovie,times(2)).initLoad()
        }
    }

    @Test
    fun `init viewModel should call load last movies, movies should change`() {
        runBlocking {
            val movies = listOf(mockedMovie.copy(id = 1))

            whenever(searchMovie.initLoad()).thenReturn(movies)

            viewModel.loadLastMoviesSearched()

            viewModel.movies.observeForever(observerMovies)

            verify(observerMovies).onChanged(viewModel.movies.value)
        }
    }

    @Test
    fun `after go to detail is called, navigate value should change`() {
        runBlocking {
            val movie = mockedMovie.copy(id = 1)

            viewModel.goToDetail(movie)

            viewModel.navigate.observeForever(observerNavigate)

            verify(observerNavigate).onChanged(viewModel.navigate.value)
        }
    }
}