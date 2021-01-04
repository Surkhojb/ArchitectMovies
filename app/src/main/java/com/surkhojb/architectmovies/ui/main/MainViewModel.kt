package com.surkhojb.architectmovies.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.surkhojb.architectmovies.data.repository.MoviesRepository
import com.surkhojb.architectmovies.model.Result
import com.surkhojb.architectmovies.ui.common.CustomScope
import com.surkhojb.architectmovies.ui.common.Event
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(private val moviesRepository: MoviesRepository): ViewModel(),CustomScope {

    private val _indicator: MutableLiveData<Boolean>  = MutableLiveData()
    val loading: LiveData<Boolean>
        get() = _indicator

    private val _movies: MutableLiveData<List<Result>>  = MutableLiveData()
    val movies: LiveData<List<Result>>
        get() = _movies

    private val _navigate: MutableLiveData<Event<Result>>  = MutableLiveData()
    val navigate: LiveData<Event<Result>>
        get() = _navigate

    init {
        initScope()
    }

    override lateinit var job: Job

    fun fetchMovies(){
        launch {
            _indicator.value = true
            _movies.value = moviesRepository.findTopRatedMovies().results
            _indicator.value = false
        }
    }

    fun goToDetail(movie: Result){
        _navigate.value = Event(movie)
    }

    override fun onCleared() {
        clearScope()
    }
}