package com.surkhojb.usecases

import com.nhaarman.mockitokotlin2.whenever
import com.surkhojb.data.repositories.MoviesRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetMovieByIdTest {

    @Mock
    lateinit var repository: MoviesRepository

    lateinit var useCase: GetMovieById

    @Before
    fun setUp(){
        useCase = GetMovieById(repository)
    }

    @Test
    fun `invoke should call moviesRepository`(){
        runBlocking {
            val movie = mockedMovie.copy(id = 1)

            whenever(repository.getMovieById(1)).thenReturn(movie)

            val result = useCase.invoke(1)

            assertEquals(movie,result)
        }
    }

}