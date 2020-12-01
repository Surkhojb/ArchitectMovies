package com.surkhojb.architectmovies.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.BasePermissionListener
import com.surkhojb.architectmovies.R
import com.surkhojb.architectmovies.data.remote.MovieDb
import com.surkhojb.architectmovies.data.repository.MoviesRepository
import com.surkhojb.architectmovies.model.Result
import com.surkhojb.architectmovies.ui.detail.DetailActivity
import com.surkhojb.architectmovies.ui.detail.ITEM_KEY
import com.surkhojb.architectmovies.ui.main.adapter.MovieAdapter
import com.surkhojb.architectmovies.ui.main.adapter.MoviewClickListener
import com.surkhojb.architectmovies.utils.launchActivity
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume

class MainActivity : AppCompatActivity() {
    lateinit var movieList: RecyclerView
    lateinit var movieAdapter: MovieAdapter
    private val moviesRepository: MoviesRepository by lazy { MoviesRepository() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureView()

        CoroutineScope(Dispatchers.IO).launch {
            val movies = moviesRepository.findTopRatedMovies().results
            withContext(Dispatchers.Main){
                movieAdapter.refreshMovies(movies)
            }
        }
    }

    private fun configureView(){
        movieList = findViewById(R.id.list_top_rated)
        movieList.hasFixedSize()
        movieAdapter = MovieAdapter()
        movieList.adapter = movieAdapter
        movieAdapter.addClickListener(object : MoviewClickListener {
            override fun onMovieClicked(movie: Result) {
                val bundle = Bundle()
                bundle.putParcelable(ITEM_KEY,movie)

                launchActivity<DetailActivity>(bundle)
            }
        })
    }
}