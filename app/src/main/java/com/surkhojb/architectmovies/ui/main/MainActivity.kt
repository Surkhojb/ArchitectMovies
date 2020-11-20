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
    lateinit var locationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureView()

        locationClient = LocationServices.getFusedLocationProviderClient(this)

        CoroutineScope(Dispatchers.IO).launch {
            val location = getLocation()
            val movies = MovieDb.service.getTopRated(
                getString(R.string.api_key),
                getRegion(location))

            withContext(Dispatchers.Main){
                movieAdapter.refreshMovies(movies.results)
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

    private suspend fun getLocation(): Location? {
        val success = requestLocationPermission()
        return if (success) findLastLocation() else null
    }

    @SuppressLint("MissingPermission")
    private suspend fun findLastLocation(): Location? {
        return suspendCancellableCoroutine { continuation ->
            locationClient.lastLocation.addOnCompleteListener {
                continuation.resume(it.result)
            }
        }
    }

    private suspend fun requestLocationPermission(): Boolean {
        return suspendCancellableCoroutine { continuation ->
            Dexter.withContext(this)
                .withPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(object : BasePermissionListener() {
                    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                        continuation.resume(true)
                    }

                    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                        continuation.resume(false)
                    }
                }).check()
        }
    }

    private fun getRegion(location: Location?): String {
        val geoCoder = Geocoder(this)
        val fromLocation = location?.let {
            geoCoder.getFromLocation(location.latitude,location.longitude,1)
        }
        return fromLocation?.firstOrNull()?.countryCode ?: "US"
    }


}