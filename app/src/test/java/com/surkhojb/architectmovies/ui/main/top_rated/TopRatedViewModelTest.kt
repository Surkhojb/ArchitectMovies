package com.surkhojb.architectmovies.ui.main.top_rated

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.surkhojb.architectmovies.ui.common.Event
import com.surkhojb.domain.Movie
import com.surkhojb.usecases.GetTopRatedMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TopRatedViewModelTest{
        @get:Rule
        val rule = InstantTaskExecutorRule()

        @Mock
        lateinit var topRatedMovies: GetTopRatedMovies

        lateinit var viewModel: TopRatedViewModel

        @Mock
        lateinit var observerIndicator: Observer<Boolean>

        @Mock
        lateinit var observerMovies: Observer<List<Movie>>

        @Mock
        lateinit var observerNavigate: Observer<Event<Movie>>

        @Before
        fun setUp(){
                viewModel = TopRatedViewModel(Dispatchers.Unconfined,topRatedMovies)
        }

        @Test
        fun `after fetching movies, loading should change`() {
                runBlocking {
                        val movies = listOf(mockedMovie.copy(id = 1))

                        whenever(topRatedMovies.invoke(false)).thenReturn(movies)

                        viewModel.fetchMovies()

                        viewModel.loading.observeForever(observerIndicator)

                        verify(observerIndicator).onChanged(viewModel.loading.value)
                }
        }

        @Test
        fun `after fetching more movies, loading should change`() {
                runBlocking {
                        val movies = listOf(mockedMovie.copy(id = 1))

                        whenever(topRatedMovies.invoke(true)).thenReturn(movies)

                        viewModel.fetchMoreMovies()

                        viewModel.loading.observeForever(observerIndicator)

                        verify(observerIndicator).onChanged(viewModel.loading.value)
                }
        }


        @Test
        fun `after fetching movies, movies value should change`() {
                runBlocking {
                        val movies = listOf(mockedMovie.copy(id = 1))

                        whenever(topRatedMovies.invoke(true)).thenReturn(movies)

                        viewModel.fetchMoreMovies()

                        viewModel.movies.observeForever(observerMovies)

                        verify(observerMovies).onChanged(viewModel.movies.value)
                }
        }

        @Test
        fun `after fetching more movies, movies value should change`() {
                runBlocking {
                        val movies = listOf(mockedMovie.copy(id = 1))

                        whenever(topRatedMovies.invoke(true)).thenReturn(movies)

                        viewModel.fetchMoreMovies()

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