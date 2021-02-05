package com.surkhojb.usecases

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.surkhojb.data.repositories.MoviesRepository
import junit.framework.Assert
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchMovieTest{

    @Mock
    lateinit var repository: MoviesRepository

    lateinit var useCase: SearchMovie

    @Before
    fun setUp(){
        useCase = SearchMovie(repository)
    }

    @Test
    fun `invoke should call moviesRepository`(){
        runBlocking {
            val movies = listOf(mockedMovie.copy(id = 1))

            whenever(repository.searchMovies("")).thenReturn(movies)

            val result = useCase.invoke("")

            Assert.assertEquals(movies,result)
        }
    }

    @Test
    fun `invoke should call saveLastSearchh`(){
        runBlocking {
            val movies = listOf(mockedMovie.copy(id = 1))

            whenever(repository.searchMovies("")).thenReturn(movies)

            val result = useCase.invoke("")

            Assert.assertEquals(movies,result)

            verify(repository).saveLastSearchh("")
        }
    }


}