package com.surkhojb.architectmovies.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.*
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.recyclerview.widget.RecyclerView
import com.surkhojb.architectmovies.R
import com.surkhojb.architectmovies.data.remote.MovieDb
import com.surkhojb.architectmovies.data.repository.MoviesRepository
import com.surkhojb.architectmovies.model.Result
import com.surkhojb.architectmovies.ui.main.adapter.MovieAdapter
import com.surkhojb.architectmovies.ui.main.adapter.MoviewClickListener
import com.surkhojb.architectmovies.utils.ThumbnailType
import com.surkhojb.architectmovies.utils.launchActivity
import com.surkhojb.architectmovies.utils.loadFromUrl
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.loading_indicator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.*

const val ITEM_KEY = "item"

class DetailActivity : AppCompatActivity() {
    lateinit var castList: RecyclerView
    lateinit var castAdapter: CastAdapter
    private val moviesRepository by lazy {  MoviesRepository() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val movie = intent.getParcelableExtra<Result>(ITEM_KEY)
        configureView()

        movie?.let {
            buildDetail(movie)
            CoroutineScope(Dispatchers.IO).launch {
                try{
                    showIndicator(true)
                    val actors = moviesRepository.loadCast(movie.id)

                    withContext(Dispatchers.Main) {
                        showIndicator(false)
                        if(actors.cast.isNullOrEmpty()) {
                            hideCastArea()
                        } else {
                            castAdapter.refreshCast(actors.cast.take(5))
                        }
                    }
                }catch (exception: Exception){
                    withContext(Dispatchers.Main) {
                        showIndicator(false)
                        hideCastArea()
                    }
                    Log.d(DetailActivity::class.java.name,exception.localizedMessage)
                }
            }
        }
    }

    private fun hideCastArea() {
        detail_cast.visibility = GONE
        list_cast.visibility = GONE
    }

    private fun configureView(){
        castList = findViewById(R.id.list_cast)
        castList.hasFixedSize()
        castAdapter = CastAdapter()
        castList.adapter = castAdapter
    }

    private fun showIndicator(show: Boolean){
        when(show){
            true -> {
                loading_indicator.visibility = VISIBLE
                castList.visibility = INVISIBLE
            }
            false -> {

                loading_indicator.visibility = GONE
                castList.visibility = VISIBLE
            }
        }
    }

    private fun buildDetail(movie: Result?){
        movie?.let {
            detail_poster.loadFromUrl(ThumbnailType.POSTER,movie.posterPath)
            detail_average.text = String.format("%s / 10",movie.voteAverage)
            detail_popularity.text = movie.popularity.toString()
            detail_count.text = movie.voteCount.toString()
            detail_title.text = movie.title
            detail_overview.text = movie.overview
            detail_movie_info.text = buildSpannedString {
                bold { append("Original language: ") }
                appendLine(movie.originalLanguage.toUpperCase(Locale.getDefault()))
                bold { append("Original title: ") }
                appendLine(movie.originalTitle)
                bold { append("Release date: ") }
                appendLine(movie.releaseDate)
            }
        }
    }
}