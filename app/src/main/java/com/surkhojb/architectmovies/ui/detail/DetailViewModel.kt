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

    private val _isFavorite: MutableLiveData<Boolean> = MutableLiveData()
    val favorite: LiveData<Boolean>
        get() = _isFavorite

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

    fun loadMovie(movieId: Int) {
        launch {
            _indicator.value = true
            _movie.value = moviesRepository.getMovieById(movieId)
            _isFavorite.value = _movie.value?.favorite
            _indicator.value = false
        }
    }

    fun onFavoriteClicked(){
        launch {
            movie.value?.let {
                val updatedMovie = it.copy(favorite = !it.favorite)
                _movie.value = updatedMovie
                moviesRepository.saveAsFavorite(updatedMovie)
                _isFavorite.value = _movie.value?.favorite
            }
        }
    }

    override fun onCleared() {
        clearScope()
    }

}