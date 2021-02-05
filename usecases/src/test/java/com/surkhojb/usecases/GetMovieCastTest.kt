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
class GetMovieCastTest {

    @Mock
    lateinit var repository: MoviesRepository

    lateinit var useCase: GetMovieCast

    @Before
    fun setUp(){
        useCase = GetMovieCast(repository)
    }

    @Test
    fun `invoke should call moviesRepository`(){
        runBlocking {
            val cast = listOf(mockedCast)

            whenever(repository.getMovieCast(1)).thenReturn(cast)

            val result = useCase.invoke(1)

            assertEquals(cast,result)
        }
    }


}