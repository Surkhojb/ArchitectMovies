package com.surkhojb.architectmovies.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.surkhojb.architectmovies.data.repository.MoviesRepository
import com.surkhojb.architectmovies.model.Cast
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

    init {
        initScope()
    }

    override lateinit var job: Job

    fun loadCast(movieId: Int){
        launch {
            _indicator.value = true
            _cast.value = moviesRepository.loadCast(movieId).cast.take(5)
            _indicator.value = false
        }
    }

    override fun onCleared() {
        clearScope()
    }

}