package com.surkhojb.architectmovies.di

import android.app.Application
import androidx.datastore.preferences.createDataStore
import androidx.room.Room
import com.surkhojb.architectmovies.common.PermissionManager
import com.surkhojb.architectmovies.common.PlayServicesDataSource
import com.surkhojb.architectmovies.data.local.DataStoreDataSource
import com.surkhojb.architectmovies.data.local.DataStoreSource
import com.surkhojb.architectmovies.data.local.MoviesDatabase
import com.surkhojb.architectmovies.data.local.RoomDataSource
import com.surkhojb.architectmovies.data.remote.TMDBDataSource
import com.surkhojb.data.datasources.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun databaseProvider(application: Application): MoviesDatabase {
        return  Room.databaseBuilder(application,MoviesDatabase::class.java,"movies")
            .build()
    }

    @Singleton
    @Provides
    fun dataLocalStoreProvider(application: Application): DataStoreSource {
        val prefSettings = application.createDataStore("settings")
        return DataStoreSource(prefSettings)
    }

    @Provides
    fun dataStoreSource(dataStoreSource: DataStoreSource): PreferencesDataSource{
        return DataStoreDataSource(dataStoreSource)
    }

    @Provides
    fun localDataSourceProvider(database: MoviesDatabase): LocalDataSource {
        return RoomDataSource(database.moviesDao())
    }

    @Provides
    fun remoteDataSource(): RemoteDataSource {
        return TMDBDataSource()
    }

    @Provides
    fun locationDataSourceProvider(application: Application): LocationDataSource {
        return PlayServicesDataSource(application)
    }

    @Provides
    fun permissionChecker(application: Application): PermissionChecker {
        return PermissionManager(application)
    }

}