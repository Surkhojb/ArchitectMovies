package com.surkhojb.data.repositories

import com.nhaarman.mockitokotlin2.whenever
import com.surkhojb.data.datasources.LocationDataSource
import com.surkhojb.data.datasources.PermissionChecker
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Assert.assertEquals

@RunWith(MockitoJUnitRunner::class)
class RegionRepositoryTest {

    @Mock
    lateinit var locationDataSource: LocationDataSource

    @Mock
    lateinit var permissionChecker: PermissionChecker

    lateinit var regionRepository: RegionRepository

    @Before
    fun setUp(){
        regionRepository = RegionRepository(locationDataSource,permissionChecker)
    }

    @Test
    fun `should return default location when permission are not granted`(){
        runBlocking {
            whenever(permissionChecker.check(listOf(PermissionChecker.Permission.COARSE_LOCATION))).thenReturn(false)

            val region = regionRepository.findLastRegion()

            assertEquals("US",region)
        }
    }

    @Test
    fun `should return location when permission are granted`(){
        runBlocking {
            whenever(permissionChecker.check(listOf(PermissionChecker.Permission.COARSE_LOCATION))).thenReturn(true)
            whenever(locationDataSource.findLastLocation()).thenReturn("ES")

            val region = regionRepository.findLastRegion()

            assertEquals("ES",region)
        }
    }
}