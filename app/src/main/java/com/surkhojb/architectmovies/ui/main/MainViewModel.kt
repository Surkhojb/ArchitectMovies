package com.surkhojb.architectmovies.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surkhojb.architectmovies.data.repository.MoviesRepository
import com.surkhojb.architectmovies.data.local.model.Movie
import com.surkhojb.architectmovies.ui.common.CustomScope
import com.surkhojb.architectmovies.ui.common.Event
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(private val moviesRepository: MoviesRepository): ViewModel(),CustomScope {

    private val _indicator: MutableLiveData<Boolean>  = MutableLiveData()
    val loading: LiveData<Boolean>
        get() = _indicator

    private val _movies: MutableLiveData<List<Movie>>  = MutableLiveData()
    val movies: LiveData<List<Movie>>
        get() = _movies

    private val _navigate: MutableLiveData<Event<Movie>>  = MutableLiveData()
    val navigate: LiveData<Event<Movie>>
        get() = _navigate

    var isLoadingMore = false

    init {
        initScope()
        fetchMovies()
    }

    override lateinit var job: Job

    fun fetchMovies(){
        launch {
            _indicator.value = true
            _movies.value = moviesRepository.findTopRatedMovies(false)
            _indicator.value = false
        }
    }

    fun fetchMoreMovies(loadMore: Boolean){
        isLoadingMore = true
        launch {
            _movies.value = moviesRepository.findTopRatedMovies(loadMore)
        }
        isLoadingMore = false
    }

    fun goToDetail(movie: Movie){
        _navigate.value = Event(movie)
    }

    override fun onCleared() {
        clearScope()
    }
}