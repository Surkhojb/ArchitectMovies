package com.surkhojb.usecases

import com.nhaarman.mockitokotlin2.whenever
import com.surkhojb.data.repositories.MoviesRepository
import com.surkhojb.domain.MovieCast
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetNewestTest {

    @Mock
    lateinit var repository: MoviesRepository

    lateinit var useCase: GetNewestMovies

    @Before
    fun setUp(){
        useCase = GetNewestMovies(repository)
    }

    @Test
    fun `invoke should call moviesRepository`(){
        runBlocking {
            val movies = listOf(mockedMovie.copy(id = 1))

            whenever(repository.getNewestMovies(false)).thenReturn(movies)

            val result = useCase.invoke(false)

            assertEquals(movies,result)
        }
    }


}