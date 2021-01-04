package com.surkhojb.architectmovies.ui.detail

import android.util.Log
import com.surkhojb.architectmovies.data.repository.MoviesRepository
import com.surkhojb.architectmovies.model.Cast
import com.surkhojb.architectmovies.ui.common.CustomScope
import kotlinx.coroutines.*

class DetailPresenter(private val moviesRepository: MoviesRepository): CustomScope {

    interface View{
        fun showIndicator(show: Boolean)
        fun refreshMovieCast(items: List<Cast>)
        fun hideCastArea()
        fun loadDetail()
    }

    private var view: View? = null

    override lateinit var job: Job

    fun onCreate(view: View){
        initScope()
        this.view = view
        view.loadDetail()
    }

    fun tryToLoadCast(movieId: Int){
        launch {
            try{
                view?.showIndicator(true)
                val actors = moviesRepository.loadCast(movieId)
                view?.showIndicator(false)
                withContext(Dispatchers.Main) {
                    if(actors.cast.isNullOrEmpty()) {
                        view?.hideCastArea()
                    } else {
                       view?.refreshMovieCast(actors.cast.take(5))
                    }
                }
            }catch (exception: Exception){
                withContext(Dispatchers.Main) {
                    view?.showIndicator(false)
                    view?.hideCastArea()
                }
                Log.d(DetailActivity::class.java.name,exception.localizedMessage)
            }
        }
    }

    fun onDestroy(){
        clearScope()
        this.view = null
    }
}