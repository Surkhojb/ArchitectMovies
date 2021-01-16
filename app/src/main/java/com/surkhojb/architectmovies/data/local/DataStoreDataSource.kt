package com.surkhojb.architectmovies.data.local

import com.surkhojb.architectmovies.MainApp
import com.surkhojb.data.datasources.PreferencesDataSource
import kotlinx.coroutines.flow.first

class DataStoreDataSource(private val dataStore: DataStoreSource): PreferencesDataSource {

    override suspend fun pageToLoad(): Int {
        return dataStore.getPage().first()
    }

    override suspend fun updatePage() {
        val page = dataStore.getPage().first().plus(1)
        return dataStore.putPage(page)
    }
}