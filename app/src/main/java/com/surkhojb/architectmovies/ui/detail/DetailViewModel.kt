package com.surkhojb.architectmovies.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surkhojb.architectmovies.data.local.model.Cast
import com.surkhojb.architectmovies.data.local.model.Movie
import com.surkhojb.architectmovies.data.repository.MoviesRepository
import com.surkhojb.architectmovies.ui.common.CustomScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailViewModel(private val moviesRepository: MoviesRepository): ViewModel(), CustomScope {

    private val _indicator: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean>
        get() = _indicator

    private val _cast: MutableLiveData<List<Cast>>  = MutableLiveData()
    val cast: LiveData<List<Cast>>
        get() = _cast

    private val _movie: MutableLiveData<Movie>  = MutableLiveData()
    val movie: LiveData<Movie>
        get() = _movie

    init {
        initScope()
    }

    override lateinit var job: Job

    fun loadCast(movieId: Int){
        launch {
            _indicator.value = true
            _cast.value = moviesRepository.loadCast(movieId)?.take(5)
            _indicator.value = false
        }
    }

    override fun onCleared() {
        clearScope()
    }

    fun loadMovie(movieId: Int) {
        launch {
            _indicator.value = true
            _movie.value = moviesRepository.getMovieById(movieId)
            _indicator.value = false
        }
    }

}