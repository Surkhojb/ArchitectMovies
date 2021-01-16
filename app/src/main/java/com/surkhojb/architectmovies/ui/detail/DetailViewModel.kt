package com.surkhojb.architectmovies.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surkhojb.architectmovies.ui.common.CustomScope
import com.surkhojb.domain.Cast
import com.surkhojb.domain.Movie
import com.surkhojb.usecases.GetMovieById
import com.surkhojb.usecases.GetMovieCast
import com.surkhojb.usecases.SaveMovieAsFavorite
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailViewModel(private val getMovieCast: GetMovieCast,
                      private val getMovieById: GetMovieById,
                      private val saveMovieAsFavorite: SaveMovieAsFavorite): ViewModel(), CustomScope {

    var movieId = -1

    private val _indicator: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean>
        get() = _indicator

    private val _cast: MutableLiveData<List<Cast>>  = MutableLiveData()
    val cast: LiveData<List<Cast>>
        get() = _cast

    private val _movie: MutableLiveData<Movie>  = MutableLiveData()
    val movie: LiveData<Movie>
        get() = _movie

    private val _isFavorite: MutableLiveData<Boolean> = MutableLiveData()
    val favorite: LiveData<Boolean>
        get() = _isFavorite

    init {
        initScope()
    }

    override lateinit var job: Job

    fun loadDetail() {
        loadMovie()
        loadCast()
    }

    private fun loadCast(){
        launch {
            _indicator.value = true
            _cast.value = getMovieCast.invoke(movieId)?.take(5)
            _indicator.value = false
        }
    }

    private fun loadMovie() {
        launch {
            _indicator.value = true
            _movie.value = getMovieById.invoke(movieId)
            _isFavorite.value = _movie.value?.favorite
            _indicator.value = false
        }
    }

    fun onFavoriteClicked(){
        launch {
            movie.value?.let {
                _movie.value = saveMovieAsFavorite.invoke(it)
                _isFavorite.value = _movie.value?.favorite
            }
        }
    }

    override fun onCleared() {
        clearScope()
    }

}