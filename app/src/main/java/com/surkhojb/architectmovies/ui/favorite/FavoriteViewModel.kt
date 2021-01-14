package com.surkhojb.architectmovies.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surkhojb.architectmovies.ui.common.CustomScope
import com.surkhojb.architectmovies.ui.common.Event
import com.surkhojb.domain.Movie
import com.surkhojb.usecases.GetFavorites
import com.surkhojb.usecases.GetNewestMovies
import com.surkhojb.usecases.GetTopRatedMovies
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FavoriteViewModel(private val getFavorites: GetFavorites): ViewModel(),CustomScope {

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
            _movies.value = getFavorites.invoke()
            _indicator.value = false
        }
    }

    fun goToDetail(movie: Movie){
        _navigate.value = Event(movie)
    }

    override fun onCleared() {
        clearScope()
    }
}