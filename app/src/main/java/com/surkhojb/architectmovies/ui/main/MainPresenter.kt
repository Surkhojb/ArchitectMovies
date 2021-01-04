package com.surkhojb.architectmovies.ui.main

import com.surkhojb.architectmovies.data.repository.MoviesRepository
import com.surkhojb.architectmovies.model.Result
import com.surkhojb.architectmovies.ui.common.CustomScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainPresenter(private val moviesRepository: MoviesRepository): CustomScope {

    override lateinit var job: Job

    interface View {
        fun showIndicator(show: Boolean)
        fun refreshMovies(items: List<Result>)
        fun goToMovieDetail(movie: Result)
    }

    private var view: View? = null

    fun onCreate(view: View){
        initScope()
        this.view = view

        launch {
            view.showIndicator(true)
            val movies = moviesRepository.findTopRatedMovies().results
            withContext(Dispatchers.Main){
                view.refreshMovies(movies)
                view.showIndicator(false)
            }
        }
    }

    fun onMovieClicked(movie: Result){
        view?.goToMovieDetail(movie)
    }

    fun onDestroy(){
        clearScope()
        this.view = null
    }

}