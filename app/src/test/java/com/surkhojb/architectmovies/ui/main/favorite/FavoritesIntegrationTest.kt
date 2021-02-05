package com.surkhojb.architectmovies.ui.main.newest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.surkhojb.architectmovies.ui.main.FakeLocalDataSource
import com.surkhojb.architectmovies.ui.main.FakeRemoteDataSource
import com.surkhojb.architectmovies.ui.main.favorite.FavoriteViewModel
import com.surkhojb.architectmovies.ui.main.initMock
import com.surkhojb.architectmovies.ui.main.top_rated.mockedMovie
import com.surkhojb.data.datasources.LocalDataSource
import com.surkhojb.data.datasources.RemoteDataSource
import com.surkhojb.domain.Movie
import com.surkhojb.usecases.GetFavorites
import com.surkhojb.usecases.GetNewestMovies
import junit.framework.Assert
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
class FavoritesIntegrationTest: AutoCloseKoinTest() {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    lateinit var viewModel: FavoriteViewModel

    @Mock
    lateinit var observerMovies: Observer<List<Movie>>

    @Before
    fun setUp(){
        val module = module {
            factory { FavoriteViewModel(get(),get()) }
            factory { GetFavorites(get()) }
        }

        initMock(module)

        viewModel = get()
    }

    @Test
    fun `data are loaded from local and return only favorite movies`() {
        runBlocking {
            val fakeLocalMovies = listOf(mockedMovie.copy(10,favorite = true), mockedMovie.copy(11),mockedMovie.copy(12,favorite = true), mockedMovie.copy(13))
            val localDataSource = get<LocalDataSource>() as FakeLocalDataSource
            localDataSource.localMovies = fakeLocalMovies

            viewModel.movies.observeForever(observerMovies)

            viewModel.fetchMovies()

            verify(observerMovies).onChanged(viewModel.movies.value)

            val liveDataMovies = viewModel.movies.value
            val favorites = fakeLocalMovies.filter { it.favorite }

            Assert.assertEquals(favorites,liveDataMovies)
        }
    }
}