package com.surkhojb.architectmovies.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.surkhojb.architectmovies.R
import com.surkhojb.architectmovies.common.PermissionManager
import com.surkhojb.architectmovies.common.PlayServicesDataSource
import com.surkhojb.architectmovies.data.local.DataStoreDataSource
import com.surkhojb.architectmovies.data.local.RoomDataSource
import com.surkhojb.architectmovies.data.remote.TMDBDataSource
import com.surkhojb.architectmovies.databinding.FragmentMainBinding
import com.surkhojb.architectmovies.ui.common.EventObserver
import com.surkhojb.architectmovies.ui.common.OnLoadMoreItems
import com.surkhojb.architectmovies.ui.main.adapter.MovieAdapter
import com.surkhojb.architectmovies.ui.main.adapter.MoviewClickListener
import com.surkhojb.architectmovies.utils.getViewModel
import com.surkhojb.data.MoviesRepository
import com.surkhojb.data.RegionRepository
import com.surkhojb.domain.Movie
import com.surkhojb.usecases.GetTopRatedMovies
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment(){
    private lateinit var binding: FragmentMainBinding
    lateinit var movieAdapter: MovieAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_main,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = getViewModel { MainViewModel(GetTopRatedMovies(
            MoviesRepository(
                RoomDataSource(),
                DataStoreDataSource(),
                TMDBDataSource(),
                RegionRepository(
                    PlayServicesDataSource(),
                    PermissionManager()
                )
            ),getString(R.string.api_key))
        )}

        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this@MainFragment

        configureView()

        viewModel.navigate.observe(viewLifecycleOwner, EventObserver { movie ->
            val action = MainFragmentDirections.actionToDetail(movie.id)
            findNavController().navigate(action)
        })
    }

    private fun configureView(){
        movieAdapter = MovieAdapter()

        list_top_rated.adapter = movieAdapter

        movieAdapter.addClickListener(object : MoviewClickListener {
            override fun onMovieClicked(movie: Movie) {
                viewModel.goToDetail(movie)
            }
        })

        list_top_rated.setOnScrollListener(object : OnLoadMoreItems(){
            override fun loadMoreItems() {
                viewModel.fetchMoreMovies()
            }
        })
    }
}