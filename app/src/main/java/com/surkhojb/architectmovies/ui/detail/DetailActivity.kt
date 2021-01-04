package com.surkhojb.architectmovies.ui.detail

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.View.INVISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.recyclerview.widget.RecyclerView
import com.surkhojb.architectmovies.R
import com.surkhojb.architectmovies.data.repository.MoviesRepository
import com.surkhojb.architectmovies.model.Cast
import com.surkhojb.architectmovies.model.Result
import com.surkhojb.architectmovies.utils.ThumbnailType
import com.surkhojb.architectmovies.utils.loadFromUrl
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.loading_indicator
import java.util.*

const val ITEM_KEY = "item"

class DetailActivity : AppCompatActivity(), DetailPresenter.View {
    lateinit var castList: RecyclerView
    lateinit var castAdapter: CastAdapter
    private var movie: Result? = null
    private var detailPresenter = DetailPresenter(MoviesRepository())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        movie = intent.getParcelableExtra(ITEM_KEY)
        detailPresenter.onCreate(this)
        configureView()
        detailPresenter.tryToLoadCast(movie?.id ?: -1)
    }

    override fun showIndicator(show: Boolean) {
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

    override fun refreshMovieCast(items: List<Cast>) {
        castAdapter.refreshCast(items)
    }

    override fun hideCastArea() {
        detail_cast.visibility = GONE
        list_cast.visibility = GONE
    }

    override fun loadDetail() {
        movie?.let {
            detail_poster.loadFromUrl(ThumbnailType.POSTER,movie?.posterPath)
            detail_average.text = String.format("%s / 10",movie?.voteAverage)
            detail_popularity.text = movie?.popularity.toString()
            detail_count.text = movie?.voteCount.toString()
            detail_title.text = movie?.title
            detail_overview.text = movie?.overview
            detail_movie_info.text = buildSpannedString {
                bold { append("Original language: ") }
                appendLine(movie?.originalLanguage?.toUpperCase(Locale.getDefault()))
                bold { append("Original title: ") }
                appendLine(movie?.originalTitle)
                bold { append("Release date: ") }
                appendLine(movie?.releaseDate)
            }
        }
    }

    override fun onDestroy() {
        detailPresenter.onDestroy()
        super.onDestroy()
    }

    private fun configureView(){
        castList = findViewById(R.id.list_cast)
        castList.hasFixedSize()
        castAdapter = CastAdapter()
        castList.adapter = castAdapter
    }}