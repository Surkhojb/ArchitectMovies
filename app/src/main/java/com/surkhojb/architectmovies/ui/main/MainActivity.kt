package com.surkhojb.architectmovies.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.surkhojb.architectmovies.R
import com.surkhojb.architectmovies.data.repository.MoviesRepository
import com.surkhojb.architectmovies.data.local.model.Movie
import com.surkhojb.architectmovies.databinding.ActivityMainBinding
import com.surkhojb.architectmovies.ui.common.BaseActivity
import com.surkhojb.architectmovies.ui.common.EventObserver
import com.surkhojb.architectmovies.ui.common.OnLoadMoreItems
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
        viewModel = getViewModel { MainViewModel(MoviesRepository()) }

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        configureView()

        viewModel.fetchMovies()

        viewModel.navigate.observe(this, EventObserver { movie ->
            val bundle = Bundle()
            bundle.putInt(ITEM_KEY,movie.id)
            launchActivity<DetailActivity>(bundle)
        })
    }

    @Suppress("DEPRECATION")
    private fun configureView(){
        movieList = findViewById(R.id.list_top_rated)
        movieList.hasFixedSize()
        movieAdapter = MovieAdapter()
        movieList.adapter = movieAdapter
        movieAdapter.addClickListener(object : MoviewClickListener {
            override fun onMovieClicked(movie: Movie) {
                viewModel.goToDetail(movie)
            }
        })
        movieList.setOnScrollListener(object : OnLoadMoreItems(){
            override fun loadMoreItems() {
                viewModel.fetchMoreMovies(true)
            }
        })
    }
}