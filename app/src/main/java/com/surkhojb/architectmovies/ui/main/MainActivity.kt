package com.surkhojb.architectmovies.ui.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.surkhojb.architectmovies.R
import com.surkhojb.architectmovies.data.repository.MoviesRepository
import com.surkhojb.architectmovies.model.Result
import com.surkhojb.architectmovies.ui.common.BaseActivity
import com.surkhojb.architectmovies.ui.detail.DetailActivity
import com.surkhojb.architectmovies.ui.detail.ITEM_KEY
import com.surkhojb.architectmovies.ui.main.adapter.MovieAdapter
import com.surkhojb.architectmovies.ui.main.adapter.MoviewClickListener
import com.surkhojb.architectmovies.utils.getViewModel
import com.surkhojb.architectmovies.utils.launchActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(){
    lateinit var movieList: RecyclerView
    lateinit var movieAdapter: MovieAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = getViewModel { MainViewModel(MoviesRepository()) }

        configureView()
        setUpObservables()

        viewModel.fetchMovies()
    }

    private fun configureView(){
        movieList = findViewById(R.id.list_top_rated)
        movieList.hasFixedSize()
        movieAdapter = MovieAdapter()
        movieList.adapter = movieAdapter
        movieAdapter.addClickListener(object : MoviewClickListener {
            override fun onMovieClicked(movie: Result) {
                viewModel.goToDetail(movie)
            }
        })
    }

    private fun setUpObservables(){
        viewModel.loading.observe(this, Observer {
            when(it){
                true -> {
                    loading_indicator.visibility = View.VISIBLE
                    movieList.visibility = View.GONE
                }
                false -> {

                    loading_indicator.visibility = View.GONE
                    movieList.visibility = View.VISIBLE
                }
            }
        })

        viewModel.movies.observe(this, Observer { movies ->
            movieAdapter.refreshMovies(movies)
        })

        viewModel.navigate.observe(this, Observer {
            it.getContentIfNotHandled()?.let { movie ->
                val bundle = Bundle()
                bundle.putParcelable(ITEM_KEY,movie)

                launchActivity<DetailActivity>(bundle)
            }
        })
    }
}