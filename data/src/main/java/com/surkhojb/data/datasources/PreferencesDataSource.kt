package com.surkhojb.data.datasources

interface PreferencesDataSource {
    suspend fun pageToLoad(): Int
    suspend fun updatePage()
}