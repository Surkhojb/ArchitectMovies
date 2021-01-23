package com.surkhojb.architectmovies.ui.main

import com.surkhojb.architectmovies.ui.main.favorite.FavoriteViewModel
import com.surkhojb.architectmovies.ui.main.newest.NewestViewModel
import com.surkhojb.architectmovies.ui.main.search.SearchViewModel
import com.surkhojb.architectmovies.ui.main.top_rated.TopRatedViewModel
import com.surkhojb.data.repositories.MoviesRepository
import com.surkhojb.usecases.*
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Singleton

@Module
class MainActivityModule{

    //region TopRated
    @Provides
    fun topRatedViewModelProvider(getTopRatedMovies: GetTopRatedMovies): TopRatedViewModel {
        return TopRatedViewModel(getTopRatedMovies)
    }

    @Provides
    fun getTopRatedProvider(moviesRepository: MoviesRepository): GetTopRatedMovies {
        return GetTopRatedMovies(moviesRepository)
    }
    //endregion

    //region Favorites
    @Provides
    fun favoriteViewModelProvider(getFavorites: GetFavorites): FavoriteViewModel {
        return FavoriteViewModel(getFavorites)
    }

    @Provides
    fun getFavoritesProvider(moviesRepository: MoviesRepository): GetFavorites {
        return GetFavorites(moviesRepository)
    }

    //endregion

    //region Newest
    @Provides
    fun newestViewModelProvider(getNewestMovies: GetNewestMovies): NewestViewModel {
        return NewestViewModel(getNewestMovies)
    }

    @Provides
    fun getNewestMoviesProvider(moviesRepository: MoviesRepository): GetNewestMovies {
        return GetNewestMovies(moviesRepository)
    }

    //endregion

    //region Search
    @Provides
    fun searchViewModelProvider(searchMovie: SearchMovie, lastSearchs: LastSearchs): SearchViewModel {
        return SearchViewModel(searchMovie,lastSearchs)
    }

    @Provides
    fun searchMoviesProvider(moviesRepository: MoviesRepository): SearchMovie {
        return SearchMovie(moviesRepository)
    }

    @Provides
    fun lastSearchhMoviesProvider(moviesRepository: MoviesRepository): LastSearchs {
        return LastSearchs(moviesRepository)
    }
    //endregion
}

@Subcomponent(modules = arrayOf(MainActivityModule::class))
interface MainActivityComponent {
    //Exposed viewModel for a easy injection
    var topRatedViewModel: TopRatedViewModel
    val newestViewModel: NewestViewModel
    val favoriteViewModel: FavoriteViewModel
    val searchViewModel: SearchViewModel
}