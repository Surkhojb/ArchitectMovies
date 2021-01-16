package com.surkhojb.architectmovies.di

import android.app.Application
import com.surkhojb.architectmovies.ui.detail.DetailViewModel
import com.surkhojb.architectmovies.ui.favorite.FavoriteViewModel
import com.surkhojb.architectmovies.ui.newest.NewestViewModel
import com.surkhojb.architectmovies.ui.search.SearchViewModel
import com.surkhojb.architectmovies.ui.top_rated.TopRatedViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(AppModule::class, DataModule::class,
    UseCasesModule::class, ViewModelsModule::class))
interface MainAppComponent {

    //Exposed viewModel for a easy injection
    var topRatedViewModel: TopRatedViewModel
    val detailViewModel: DetailViewModel
    val newestViewModel: NewestViewModel
    val favoriteViewModel: FavoriteViewModel
    val searchViewModel: SearchViewModel

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): MainAppComponent
    }
}