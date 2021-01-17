package com.surkhojb.architectmovies.ui.main.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surkhojb.architectmovies.ui.common.CustomScope
import com.surkhojb.architectmovies.ui.common.Event
import com.surkhojb.domain.Movie
import com.surkhojb.usecases.LastSearchs
import com.surkhojb.usecases.SearchMovie
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchViewModel(private val searchMovies: SearchMovie,
private val lastSearchs: LastSearchs) : ViewModel(), CustomScope {
    private val _indicator: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean>
        get() = _indicator

    private val _movies: MutableLiveData<List<Movie>> = MutableLiveData()
    val movies: LiveData<List<Movie>>
        get() = _movies

    private val _searchs: MutableLiveData<List<String>> = MutableLiveData()
    val searchs: LiveData<List<String>>
        get() = _searchs

    private val _navigate: MutableLiveData<Event<Movie>> = MutableLiveData()
    val navigate: LiveData<Event<Movie>>
        get() = _navigate

    var isLoadingMore = false

    init {
        initScope()
        recoverLastSearchs()
    }

    override lateinit var job: Job

    fun searchMovie(query: String){
        launch {
            _indicator.value = true
            _movies.value = searchMovies.invoke(query)
            lastSearchs.put(query)
            _indicator.value = false
        }
    }

    fun recoverLastSearchs(){
        launch {
            _indicator.value = true
            _searchs.value = lastSearchs.get()
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