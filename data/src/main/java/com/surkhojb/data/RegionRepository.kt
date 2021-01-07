package com.surkhojb.data

import com.surkhojb.data.PermissionChecker.Permission

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

interface LocationDataSource{
    suspend fun findLastLocation(): String?
}

interface PermissionChecker{
    enum class Permission { COARSE_LOCATION }
    suspend fun check(permissions: List<Permission>): Boolean
}