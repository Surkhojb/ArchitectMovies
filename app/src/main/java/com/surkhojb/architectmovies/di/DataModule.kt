package com.surkhojb.architectmovies.di

import com.surkhojb.data.datasources.*
import com.surkhojb.data.repositories.MoviesRepository
import com.surkhojb.data.repositories.RegionRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun regionRepositoryProvider(locationDataSource: LocationDataSource,
                                 permissionChecker: PermissionChecker): RegionRepository{
        return RegionRepository(locationDataSource,permissionChecker)
    }

    @Provides
    fun moviesRepositoryProvider(localDataSource: LocalDataSource,
                                 preferencesDataSource: PreferencesDataSource,
                                 remoteDataSource: RemoteDataSource,
                                 regionRepository: RegionRepository
    ): MoviesRepository {
        return MoviesRepository(localDataSource,
            preferencesDataSource,
            remoteDataSource,
            regionRepository)
    }
}