package com.surkhojb.architectmovies.data.local

import com.surkhojb.architectmovies.MainApp
import com.surkhojb.data.datasources.PreferencesDataSource
import kotlinx.coroutines.flow.first

class DataStoreDataSource(private val dataStore: DataStoreSource): PreferencesDataSource {

    override suspend fun pageToLoad(type: String): Int {
        return dataStore.getPage(type).first()
    }

    override suspend fun updatePage(type: String) {
        val page = dataStore.getPage(type).first().plus(1)
        return dataStore.putPage(page,type)
    }
}