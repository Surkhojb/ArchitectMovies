package com.surkhojb.architectmovies.ui.main.newest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.surkhojb.architectmovies.ui.main.FakeLocalDataSource
import com.surkhojb.architectmovies.ui.main.FakeRemoteDataSource
import com.surkhojb.architectmovies.ui.main.initMock
import com.surkhojb.architectmovies.ui.main.top_rated.mockedMovie
import com.surkhojb.data.datasources.LocalDataSource
import com.surkhojb.data.datasources.RemoteDataSource
import com.surkhojb.domain.Movie
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
class NewestIntegrationTest: AutoCloseKoinTest() {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    lateinit var viewModel: NewestViewModel

    @Mock
    lateinit var observerMovies: Observer<List<Movie>>

    @Before
    fun setUp(){
        val module = module {
            factory { NewestViewModel(get(),get()) }
            factory { GetNewestMovies(get()) }
        }

        initMock(module)

        viewModel = get()
    }

    @Test
    fun `data is loaded from remote when local db is empty`() {
        runBlocking {
            val remoteDataSource = get<RemoteDataSource>() as FakeRemoteDataSource
            val movies = remoteDataSource.movies

            viewModel.movies.observeForever(observerMovies)

            viewModel.fetchMovies()

            verify(observerMovies, times(2)).onChanged(viewModel.movies.value)

            val liveDataMovies = viewModel.movies.value

            Assert.assertEquals(movies,liveDataMovies)
        }
    }

    @Test
    fun `data is loaded from local when local db is not empty`() {
        runBlocking {
            val fakeLocalMovies = listOf(mockedMovie.copy(10), mockedMovie.copy(11))
            val localDataSource = get<LocalDataSource>() as FakeLocalDataSource
            localDataSource.localMovies = fakeLocalMovies

            viewModel.movies.observeForever(observerMovies)

            viewModel.fetchMovies()

            Assert.assertEquals(fakeLocalMovies,viewModel.movies.value)
        }
    }

    @Test
    fun `data is loaded from remote when load more is true`() {
        runBlocking {
            val remoteDataSource = get<RemoteDataSource>() as FakeRemoteDataSource
            val movies = remoteDataSource.movies

            viewModel.movies.observeForever(observerMovies)

            viewModel.fetchMoreMovies()

            verify(observerMovies, times(2)).onChanged(viewModel.movies.value)

            val liveDataMovies = viewModel.movies.value

            Assert.assertEquals(movies,liveDataMovies)
        }
    }
}