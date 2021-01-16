package com.surkhojb.data.datasources

interface LocationDataSource{
    suspend fun findLastLocation(): String?
}