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
class GetFavoritesTest {

    @Mock
    lateinit var repository: MoviesRepository

    lateinit var useCase: GetFavorites

    @Before
    fun setUp(){
        useCase = GetFavorites(repository)
    }

    @Test
    fun `invoke should call moviesRepository`(){
        runBlocking {
            val favorites = listOf(mockedMovie.copy(id = 1))

            whenever(repository.getFavorites()).thenReturn(favorites)

            val result = useCase.invoke()

            assertEquals(favorites,result)
        }
    }

}