package com.surkhojb.architectmovies.data.local

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


private val PAGE_TOP_RATED = preferencesKey<Int>("page_top_rated")
private val PAGE_NEWEST = preferencesKey<Int>("page_newest")

private const val TOP_RATED = "top_rated"
private const val NEWEST = "newest"

class DataStoreSource(private val dataStore: DataStore<Preferences>) {
    suspend fun putPage(page: Int, type: String){
        dataStore.edit { pref ->
                when(type){
                    TOP_RATED -> { pref[PAGE_TOP_RATED] = page}
                    NEWEST -> { pref[PAGE_NEWEST] = page}
                }
        }
    }

    fun getPage(type: String): Flow<Int> {
       return dataStore.data
            .catch { ex ->
                when(ex){
                    is IOException -> Log.e("${DataStoreSource::class.java}", "Error trying to get data from dataStore")
                    else -> throw ex
                }
            }.map {pref ->
                   when(type){
                       TOP_RATED -> { pref[PAGE_TOP_RATED] ?: 1}
                       NEWEST -> { pref[PAGE_NEWEST] ?: 1}
                       else -> 1
                   }

            }
    }
}