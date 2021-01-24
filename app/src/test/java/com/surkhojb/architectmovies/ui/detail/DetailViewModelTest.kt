package com.surkhojb.architectmovies.ui.main.top_rated

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.surkhojb.architectmovies.ui.common.Event
import com.surkhojb.architectmovies.ui.detail.DetailViewModel
import com.surkhojb.domain.Cast
import com.surkhojb.domain.Movie
import com.surkhojb.usecases.GetMovieById
import com.surkhojb.usecases.GetMovieCast
import com.surkhojb.usecases.GetTopRatedMovies
import com.surkhojb.usecases.SaveMovieAsFavorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest{
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var movieById: GetMovieById

    @Mock
    lateinit var movieCast: GetMovieCast

    @Mock
    lateinit var saveMovieAsFavorite: SaveMovieAsFavorite

    @Mock
    lateinit var observerIndicator: Observer<Boolean>

    @Mock
    lateinit var observerMovie: Observer<Movie>

    @Mock
    lateinit var observerCast: Observer<List<Cast>>

    @Mock
    lateinit var observerFavorite: Observer<Boolean>

    lateinit var viewModel: DetailViewModel

    @Before
    fun setUp(){
        viewModel = DetailViewModel(Dispatchers.Unconfined,movieCast,movieById,saveMovieAsFavorite)
    }

    @Test
    fun `getting movie by id, should change loading`() {
        runBlocking {
            val movie = mockedMovie.copy(id = 1)

            whenever(movieById.invoke(1)).thenReturn(movie)

            viewModel.loading.observeForever(observerIndicator)

            viewModel.loadDetail()

            verify(observerIndicator,times(2)).onChanged(viewModel.loading.value)
        }
    }

    @Test
    fun `getting movie by id, should change movie value`() {
        runBlocking {
            val movie = mockedMovie.copy(id = 1)
            whenever(movieById.invoke(1)).thenReturn(movie)

            viewModel.movie.observeForever(observerMovie)

            viewModel.loadDetail()

            verify(observerMovie).onChanged(viewModel.movie.value)
        }
    }


    @Test
    fun `getting movie by id, should change cast value`() {
        runBlocking {
            val movie = mockedMovie.copy(id = 1)
            whenever(movieById.invoke(1)).thenReturn(movie)

            viewModel.cast.observeForever(observerCast)

            viewModel.loadDetail()

            verify(observerCast).onChanged(viewModel.cast.value)
        }
    }

    @Test
    fun `after click fab button, should change favorite state`() {
        runBlocking {
            val movie = mockedMovie.copy(id = 1)

            whenever(saveMovieAsFavorite.invoke(movie)).thenReturn(movie.copy(favorite = !movie.favorite))

            viewModel.favorite.observeForever(observerFavorite)

            viewModel.onFavoriteClicked()

            verify(observerFavorite).onChanged(viewModel.favorite.value)
        }
    }
}