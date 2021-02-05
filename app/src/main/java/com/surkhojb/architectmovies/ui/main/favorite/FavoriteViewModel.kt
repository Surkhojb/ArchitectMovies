package com.surkhojb.architectmovies.ui.main.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surkhojb.architectmovies.ui.common.BaseViewModel
import com.surkhojb.architectmovies.ui.common.CustomScope
import com.surkhojb.architectmovies.ui.common.Event
import com.surkhojb.domain.Movie
import com.surkhojb.usecases.GetFavorites
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FavoriteViewModel(
        private val uiDispatcher: CoroutineDispatcher,
        private val getFavorites: GetFavorites): BaseViewModel(uiDispatcher) {

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
        fetchMovies()
    }

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
}