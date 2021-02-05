package com.surkhojb.architectmovies.ui.main.favorite


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.surkhojb.architectmovies.ui.common.Event
import com.surkhojb.architectmovies.ui.main.top_rated.mockedMovie
import com.surkhojb.domain.Movie
import com.surkhojb.usecases.GetFavorites
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoritesViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getFavorites: GetFavorites

    @Mock
    lateinit var observerIndicator: Observer<Boolean>

    @Mock
    lateinit var observerMovies: Observer<List<Movie>>

    @Mock
    lateinit var observerNavigate: Observer<Event<Movie>>

    lateinit var viewModel: FavoriteViewModel

    @Before
    fun setUp(){
        viewModel = FavoriteViewModel(Dispatchers.Unconfined,getFavorites)
    }

    @Test
    fun `fetching movies, loading should change`() {
        runBlocking {
            val movies = listOf(mockedMovie.copy(id = 1))

            whenever(getFavorites.invoke()).thenReturn(movies)

            viewModel.fetchMovies()

            viewModel.loading.observeForever(observerIndicator)

            verify(observerIndicator).onChanged(viewModel.loading.value)
        }
    }

    @Test
    fun `fetching movies, movies value should change`() {
        runBlocking {
            val movies = listOf(mockedMovie.copy(id = 1))

            whenever(getFavorites.invoke()).thenReturn(movies)

            viewModel.fetchMovies()

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