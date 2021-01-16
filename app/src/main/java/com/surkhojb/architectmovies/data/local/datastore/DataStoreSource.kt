package com.surkhojb.architectmovies.data.local

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import kotlinx.coroutines.flow.Flow


private val PAGE_KEY = preferencesKey<Int>("lastPage")

class DataStoreSource(private val dataStore: DataStore<Preferences>) {
    suspend fun putPage(page: Int){
        dataStore.edit { pref ->
            pref[PAGE_KEY] = page
        }
    }

    fun getPage(): Flow<Int> {
       return dataStore.data
            .catch { ex ->
                when(ex){
                    is IOException -> Log.e("${DataStoreSource::class.java}", "Error trying to get data from dataStore")
                    else -> throw ex
                }
            }.map {pref ->
                pref[PAGE_KEY] ?: 1
            }
    }
}