package com.surkhojb.data.datasources

interface PreferencesDataSource {
    suspend fun pageToLoad(type: String): Int?
    suspend fun updatePage(type: String)
}