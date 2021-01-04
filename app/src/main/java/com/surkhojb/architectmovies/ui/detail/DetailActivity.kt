package com.surkhojb.architectmovies.ui.detail

import android.os.Bundle
import android.view.View.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.surkhojb.architectmovies.R
import com.surkhojb.architectmovies.data.repository.MoviesRepository
import com.surkhojb.architectmovies.model.Result
import com.surkhojb.architectmovies.utils.ThumbnailType
import com.surkhojb.architectmovies.utils.getViewModel
import com.surkhojb.architectmovies.utils.loadFromUrl
import kotlinx.android.synthetic.main.activity_detail.*
import java.util.*

const val ITEM_KEY = "item"

class DetailActivity : AppCompatActivity() {
    lateinit var castList: RecyclerView
    lateinit var castAdapter: CastAdapter
    lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        viewModel =  getViewModel { DetailViewModel(MoviesRepository()) }

        val movie = intent.getParcelableExtra<Result>(ITEM_KEY)
        configureView()
        setUpObservables()
        buildDetail(movie)

        viewModel.loadCast(movie?.id ?: -1 )
    }

    private fun configureView(){
        castList = findViewById(R.id.list_cast)
        castList.hasFixedSize()
        castAdapter = CastAdapter()
        castList.adapter = castAdapter
    }

    private fun setUpObservables(){
        viewModel.loading.observe(this, Observer {
            when(it){
                true -> {
                    loading_indicator.visibility = VISIBLE
                    castList.visibility = INVISIBLE
                }
                false -> {

                    loading_indicator.visibility = GONE
                    castList.visibility = VISIBLE
                }
            }
        })

        viewModel.cast.observe(this, Observer { actors ->
        if(actors.isNullOrEmpty()) {
            detail_cast.visibility = GONE
            list_cast.visibility = GONE
        } else {
            castAdapter.refreshCast(actors)
        }
        })
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