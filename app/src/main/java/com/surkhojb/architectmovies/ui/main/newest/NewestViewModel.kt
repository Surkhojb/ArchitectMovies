package com.surkhojb.architectmovies.ui.main.newest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surkhojb.architectmovies.ui.common.CustomScope
import com.surkhojb.architectmovies.ui.common.Event
import com.surkhojb.domain.Movie
import com.surkhojb.usecases.GetNewestMovies
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NewestViewModel(private val getNewestMovies: GetNewestMovies): ViewModel(),CustomScope {

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
            _movies.value = getNewestMovies.invoke()
            _indicator.value = false
        }
    }

    fun fetchMoreMovies(){
        isLoadingMore = true
        launch {
            _movies.value = getNewestMovies.invoke(true)
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