package com.surkhojb.architectmovies.ui.main.top_rated

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.surkhojb.architectmovies.ui.detail.DetailViewModel
import com.surkhojb.architectmovies.ui.main.*
import com.surkhojb.data.datasources.LocalDataSource
import com.surkhojb.data.datasources.RemoteDataSource
import com.surkhojb.domain.Movie
import com.surkhojb.domain.MovieCast
import com.surkhojb.usecases.GetMovieById
import com.surkhojb.usecases.GetMovieCast
import com.surkhojb.usecases.GetTopRatedMovies
import com.surkhojb.usecases.SaveMovieAsFavorite
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
class DetailIntegrationTest: AutoCloseKoinTest() {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    lateinit var viewModel: DetailViewModel

    @Mock
    lateinit var observerMovie: Observer<Movie>

    @Before
    fun setUp(){
        val module = module {
            factory { DetailViewModel(get(),get(),get(),get()) }
            factory { GetMovieById(get()) }
            factory { GetMovieCast(get()) }
            factory { SaveMovieAsFavorite(get()) }
        }

        initMock(module)

        viewModel = get()
    }

    @Test
    fun `data is loaded from db to get movie by id`() {
        runBlocking {
            val localDataSource = get<LocalDataSource>() as FakeLocalDataSource
            localDataSource.localMovies = fakeMovies
            val movie = fakeMovies.first { it.id == 1 }

            viewModel.movieId = movie.id

            viewModel.movie.observeForever(observerMovie)

            viewModel.loadDetail()

            verify(observerMovie).onChanged(viewModel.movie.value)

            val liveDataMovie = viewModel.movie.value

            assertEquals(movie,liveDataMovie)
        }
    }

    @Test
    fun `data cast is loaded from db`() {
        runBlocking {
            val expectedCast = fakeCast

            val fakeLocalMovies = listOf(mockedMovie.copy(10,cast = MovieCast(fakeCast)), mockedMovie.copy(11))

            val localDataSource = get<LocalDataSource>() as FakeLocalDataSource
            localDataSource.localMovies = fakeLocalMovies

            viewModel.movieId = 10

            viewModel.movie.observeForever(observerMovie)

            viewModel.loadDetail()

            val liveDataCast = viewModel.movie.value?.cast?.cast

            assertEquals(expectedCast,liveDataCast)
        }
    }

    @Test
    fun `data is saved into db after favorite or unfavorite movie `() {
        runBlocking {
            val localDataSource = get<LocalDataSource>() as FakeLocalDataSource
            localDataSource.localMovies = fakeMovies

            var movie = fakeMovies.first { it.id == 1 }
            movie.favorite = false

            viewModel.movieId = 10

            viewModel.movie.observeForever(observerMovie)

            viewModel.onFavoriteClicked()

            verify(observerMovie).onChanged(viewModel.movie.value)

            val liveDataMovie = viewModel.movie.value

            assertEquals(movie.favorite,liveDataMovie?.favorite)
        }
    }
}