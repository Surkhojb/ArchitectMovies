package com.surkhojb.usecases

import com.nhaarman.mockitokotlin2.verify
import com.surkhojb.data.repositories.MoviesRepository
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SaveMovieAsFavoriteTest{

    @Mock
    lateinit var repository: MoviesRepository

    lateinit var useCase: SaveMovieAsFavorite

    @Before
    fun setUp(){
        useCase = SaveMovieAsFavorite(repository)
    }

    @Test
    fun `invoke should call moviesRepository`(){
        runBlocking {
            val movie = mockedMovie.copy(id = 1)

            repository.saveMovieAsFavorite(movie)

            useCase.invoke(movie)

           verify(repository).saveMovieAsFavorite(movie)
        }
    }

    @Test
    fun `when movie is favorite should become unfavorite`(){
        runBlocking {
            val movie = mockedMovie.copy(id = 1,favorite = true)

            val result = useCase.invoke(movie)

            assertFalse(result.favorite)

        }
    }

    @Test
    fun `when movie is unfavorite should become favorite`(){
        runBlocking {
            val movie = mockedMovie.copy(id = 1,favorite = false)

            val result = useCase.invoke(movie)

            assertTrue(result.favorite)

        }
    }
}