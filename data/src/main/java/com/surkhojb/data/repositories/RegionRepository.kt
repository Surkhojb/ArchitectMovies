package com.surkhojb.data.repositories

import com.surkhojb.data.datasources.LocationDataSource
import com.surkhojb.data.repositories.PermissionChecker.Permission

class RegionRepository(private val locationDataSource: LocationDataSource,
                       private val permissionChecker: PermissionChecker) {

    suspend fun findLastRegion(): String{
        return if(permissionChecker.check(listOf(Permission.COARSE_LOCATION))){
            locationDataSource.findLastLocation() ?: "US"
        }else {
            "US"
        }
    }
}



interface PermissionChecker{
    enum class Permission { COARSE_LOCATION }
    suspend fun check(permissions: List<Permission>): Boolean
}