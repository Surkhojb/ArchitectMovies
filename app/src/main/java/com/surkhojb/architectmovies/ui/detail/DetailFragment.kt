package com.surkhojb.architectmovies.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.surkhojb.architectmovies.R
import com.surkhojb.architectmovies.common.PermissionManager
import com.surkhojb.architectmovies.common.PlayServicesDataSource
import com.surkhojb.architectmovies.data.local.DataStoreDataSource
import com.surkhojb.architectmovies.data.local.RoomDataSource
import com.surkhojb.architectmovies.data.remote.TMDBDataSource
import com.surkhojb.architectmovies.databinding.FragmentDetailBinding
import com.surkhojb.architectmovies.utils.getViewModel
import com.surkhojb.data.repositories.MoviesRepository
import com.surkhojb.data.repositories.RegionRepository
import com.surkhojb.usecases.GetMovieById
import com.surkhojb.usecases.GetMovieCast
import com.surkhojb.usecases.SaveMovieAsFavorite
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.fragment_detail,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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

        binding.lifecycleOwner = this@DetailFragment
        binding.viewModel = this.viewModel

        list_cast.adapter = CastAdapter()

    }

}