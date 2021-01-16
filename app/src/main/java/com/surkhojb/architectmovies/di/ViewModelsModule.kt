package com.surkhojb.architectmovies.di

import com.surkhojb.architectmovies.ui.detail.DetailViewModel
import com.surkhojb.architectmovies.ui.favorite.FavoriteViewModel
import com.surkhojb.architectmovies.ui.newest.NewestViewModel
import com.surkhojb.architectmovies.ui.search.SearchViewModel
import com.surkhojb.architectmovies.ui.top_rated.TopRatedViewModel
import com.surkhojb.usecases.*
import dagger.Module
import dagger.Provides

@Module
class ViewModelsModule {

    @Provides
    fun topRatedViewModelProvider(getTopRatedMovies: GetTopRatedMovies): TopRatedViewModel {
        return TopRatedViewModel(getTopRatedMovies)
    }

    @Provides
    fun detailViewModelProvider(getMovieCast: GetMovieCast,
                                getMovieById: GetMovieById,
                                saveMovieAsFavorite: SaveMovieAsFavorite): DetailViewModel {

        return DetailViewModel(getMovieCast, getMovieById, saveMovieAsFavorite)
    }

    @Provides
    fun favoriteViewModelProvider(getFavorites: GetFavorites): FavoriteViewModel {
        return FavoriteViewModel(getFavorites)
    }

    @Provides
    fun newestViewModelProvider(getNewestMovies: GetNewestMovies): NewestViewModel {
        return NewestViewModel(getNewestMovies)
    }

    @Provides
    fun searchViewModelProvider(searchMovie: SearchMovie): SearchViewModel{
        return SearchViewModel(searchMovie)
    }

}