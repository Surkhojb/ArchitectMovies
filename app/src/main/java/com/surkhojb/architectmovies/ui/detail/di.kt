package com.surkhojb.architectmovies.ui.detail

import com.surkhojb.data.repositories.MoviesRepository
import com.surkhojb.usecases.GetFavorites
import com.surkhojb.usecases.GetMovieById
import com.surkhojb.usecases.GetMovieCast
import com.surkhojb.usecases.SaveMovieAsFavorite
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class DetailActivityModule{

    //region Detail
    @Provides
    fun detailViewModelProvider(getMovieCast: GetMovieCast,
                                getMovieById: GetMovieById,
                                saveMovieAsFavorite: SaveMovieAsFavorite
    ): DetailViewModel {

        return DetailViewModel(getMovieCast, getMovieById, saveMovieAsFavorite)
    }

    @Provides
    fun getMovieCastProvider(moviesRepository: MoviesRepository): GetMovieCast {
        return GetMovieCast(moviesRepository)
    }

    @Provides
    fun getMovieByIdProvider(moviesRepository: MoviesRepository): GetMovieById {
        return GetMovieById(moviesRepository)
    }

    @Provides
    fun saveMovieAsFavoriteProvider(moviesRepository: MoviesRepository): SaveMovieAsFavorite {
        return SaveMovieAsFavorite(moviesRepository)
    }

    //endregion
}

@Subcomponent(modules = arrayOf(DetailActivityModule::class))
interface DetailActivityComponent {
    //Exposed viewModel for an easy injection
    val detailViewModel: DetailViewModel
}