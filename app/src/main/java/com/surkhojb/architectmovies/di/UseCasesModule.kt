package com.surkhojb.architectmovies.di

import com.surkhojb.data.repositories.MoviesRepository
import com.surkhojb.usecases.*
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {

    @Provides
    fun getTopRatedProvider(moviesRepository: MoviesRepository): GetTopRatedMovies {
        return GetTopRatedMovies(moviesRepository)
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
    fun getFavoritesProvider(moviesRepository: MoviesRepository): GetFavorites {
        return GetFavorites(moviesRepository)
    }

    @Provides
    fun getNewestMoviesProvider(moviesRepository: MoviesRepository): GetNewestMovies {
        return GetNewestMovies(moviesRepository)
    }

    @Provides
    fun saveMovieAsFavoriteProvider(moviesRepository: MoviesRepository): SaveMovieAsFavorite {
        return SaveMovieAsFavorite(moviesRepository)
    }

    @Provides
    fun searchMoviesProvider(moviesRepository: MoviesRepository): SearchMovie {
        return SearchMovie(moviesRepository)
    }
}