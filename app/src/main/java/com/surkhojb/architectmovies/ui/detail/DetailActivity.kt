package com.surkhojb.architectmovies.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.navArgs
import com.surkhojb.architectmovies.R
import com.surkhojb.architectmovies.common.PermissionManager
import com.surkhojb.architectmovies.common.PlayServicesDataSource
import com.surkhojb.architectmovies.data.local.DataStoreDataSource
import com.surkhojb.architectmovies.data.local.RoomDataSource
import com.surkhojb.architectmovies.data.remote.TMDBDataSource
import com.surkhojb.architectmovies.databinding.ActivityDetailBinding
import com.surkhojb.architectmovies.utils.getViewModel
import com.surkhojb.data.repositories.MoviesRepository
import com.surkhojb.data.repositories.RegionRepository
import com.surkhojb.usecases.GetMovieById
import com.surkhojb.usecases.GetMovieCast
import com.surkhojb.usecases.SaveMovieAsFavorite
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding
    private val args: DetailActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail)

        val moviesRepository = MoviesRepository(
                RoomDataSource(),
                DataStoreDataSource(),
                TMDBDataSource(),
                RegionRepository(
                        PlayServicesDataSource(),
                        PermissionManager())
        )
        viewModel =  getViewModel {
            DetailViewModel(
                    GetMovieCast(moviesRepository),
                    GetMovieById(moviesRepository),
                    SaveMovieAsFavorite(moviesRepository)) }
        viewModel.movieId = args.movieId
        viewModel.loadDetail()

        binding.lifecycleOwner = this@DetailActivity
        binding.viewModel = this.viewModel

        list_cast.adapter = CastAdapter()
    }

}