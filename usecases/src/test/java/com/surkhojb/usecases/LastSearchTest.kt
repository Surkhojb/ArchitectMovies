package com.surkhojb.usecases

import com.nhaarman.mockitokotlin2.verify
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
class LastSearchTest {

    @Mock
    lateinit var repository: MoviesRepository

    lateinit var useCase: LastSearchs

    @Before
    fun setUp(){
        useCase = LastSearchs(repository)
    }

    @Test
    fun `invoke should call moviesRepository`(){
        runBlocking {
            val lastSearchs = listOf("abc","dfg","hij")
            whenever(repository.getLastSearchs()).thenReturn(lastSearchs)

            val result = useCase.get()

            assertEquals(lastSearchs,result)
        }
    }

}