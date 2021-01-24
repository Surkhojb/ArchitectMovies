package com.surkhojb.architectmovies

import android.app.Application
import com.surkhojb.architectmovies.data.local.DataStoreSource
import com.surkhojb.architectmovies.data.local.MoviesDatabase
import com.surkhojb.architectmovies.data.local.RoomDataSource
import com.surkhojb.architectmovies.data.remote.TMDBDataSource
import androidx.datastore.preferences.createDataStore
import com.surkhojb.architectmovies.common.PermissionManager
import com.surkhojb.architectmovies.common.PlayServicesDataSource
import com.surkhojb.architectmovies.data.local.DataStoreDataSource
import com.surkhojb.architectmovies.data.remote.model.Movie
import com.surkhojb.architectmovies.ui.MainActivity
import com.surkhojb.architectmovies.ui.detail.DetailActivity
import com.surkhojb.architectmovies.ui.detail.DetailViewModel
import com.surkhojb.architectmovies.ui.main.favorite.FavoriteViewModel
import com.surkhojb.architectmovies.ui.main.favorite.FavoritesFragment
import com.surkhojb.architectmovies.ui.main.newest.NewestFragment
import com.surkhojb.architectmovies.ui.main.newest.NewestViewModel
import com.surkhojb.architectmovies.ui.main.search.SearchFragment
import com.surkhojb.architectmovies.ui.main.search.SearchViewModel
import com.surkhojb.architectmovies.ui.main.top_rated.TopRatedFragment
import com.surkhojb.architectmovies.ui.main.top_rated.TopRatedViewModel
import com.surkhojb.data.datasources.*
import com.surkhojb.data.repositories.MoviesRepository
import com.surkhojb.data.repositories.RegionRepository
import com.surkhojb.usecases.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initKoin() {
    startKoin {
        androidLogger()
        androidContext(this@initKoin)
        modules(listOf(appModule, dataModule, scopeModules))
    }
}


    private val appModule = module {
        single { MoviesDatabase.build(get()).moviesDao() }
        factory<LocalDataSource> { RoomDataSource(get()) }
        factory {
            val settings = androidApplication().createDataStore("settings")
            DataStoreSource(settings)
        }
        factory<PreferencesDataSource> { DataStoreDataSource(get())}
        factory<RemoteDataSource> { TMDBDataSource() }
        factory<LocationDataSource> { PlayServicesDataSource(get())}
        factory<PermissionChecker> { PermissionManager(get()) }
        single<CoroutineDispatcher> { Dispatchers.Main }
    }

    private val dataModule = module {
        factory { RegionRepository(get(), get()) }
        factory { MoviesRepository(get(), get(), get(), get()) }
    }

    private val scopeModules = module {
        scope(named<TopRatedFragment>()){
            viewModel { TopRatedViewModel(get(),get()) }
            scoped { GetTopRatedMovies(get()) }
        }
        scope(named<NewestFragment>()) {
            viewModel { NewestViewModel(get(),get()) }
            scoped { GetNewestMovies(get()) }
        }

        scope(named<SearchFragment>()) {
            viewModel { SearchViewModel(get(),get(),get()) }
            scoped { SearchMovie(get()) }
            scoped { LastSearchs(get())}
        }

        scope(named<FavoritesFragment>()) {
            viewModel { FavoriteViewModel(get(),get()) }
            scoped { GetFavorites(get()) }
        }

        scope(named<DetailActivity>()) {
            viewModel { DetailViewModel(get(), get(), get(),get()) }
            scoped { GetMovieCast(get()) }
            scoped { GetMovieById(get()) }
            scoped { SaveMovieAsFavorite(get()) }
        }
    }