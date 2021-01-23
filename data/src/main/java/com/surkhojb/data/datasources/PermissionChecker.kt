package com.surkhojb.data.datasources

interface PermissionChecker{
    enum class Permission { COARSE_LOCATION }
    suspend fun check(permissions: List<Permission>): Boolean
}