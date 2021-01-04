package com.surkhojb.architectmovies.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.surkhojb.architectmovies.R
import com.surkhojb.architectmovies.data.repository.MoviesRepository
import com.surkhojb.architectmovies.model.Result
import com.surkhojb.architectmovies.ui.detail.DetailActivity
import com.surkhojb.architectmovies.ui.detail.ITEM_KEY
import com.surkhojb.architectmovies.ui.main.adapter.MovieAdapter
import com.surkhojb.architectmovies.ui.main.adapter.MoviewClickListener
import com.surkhojb.architectmovies.utils.launchActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainPresenter.View {

    lateinit var movieList: RecyclerView
    lateinit var movieAdapter: MovieAdapter
    private val mainPresenter = MainPresenter( MoviesRepository())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainPresenter.onCreate(this)
        configureView()
    }

    override fun showIndicator(show: Boolean){
        when(show){
            true -> {
                loading_indicator.visibility = View.VISIBLE
                movieList.visibility = View.GONE
            }
            false -> {

                loading_indicator.visibility = View.GONE
                movieList.visibility = View.VISIBLE
            }
        }
    }

    override fun refreshMovies(items: List<Result>) {
        movieAdapter.refreshMovies(items)
    }

    override fun goToMovieDetail(movie: Result) {
        val bundle = Bundle()
        bundle.putParcelable(ITEM_KEY,movie)

        launchActivity<DetailActivity>(bundle)
    }

    override fun onDestroy() {
        mainPresenter.onDestroy()
        super.onDestroy()
    }

    private fun configureView(){
        movieList = findViewById(R.id.list_top_rated)
        movieList.hasFixedSize()
        movieAdapter = MovieAdapter()
        movieList.adapter = movieAdapter
        movieAdapter.addClickListener(object : MoviewClickListener {
            override fun onMovieClicked(movie: Result) {
                mainPresenter.onMovieClicked(movie)
            }
        })
    }
}