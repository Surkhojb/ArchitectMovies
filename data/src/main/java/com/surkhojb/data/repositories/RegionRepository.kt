package com.surkhojb.data.repositories

import com.surkhojb.data.datasources.LocationDataSource
import com.surkhojb.data.datasources.PermissionChecker

class RegionRepository(private val locationDataSource: LocationDataSource,
                       private val permissionChecker: PermissionChecker) {

    suspend fun findLastRegion(): String{
        return if(permissionChecker.check(listOf(PermissionChecker.Permission.COARSE_LOCATION))){
            locationDataSource.findLastLocation() ?: "US"
        }else {
            "US"
        }
    }
}