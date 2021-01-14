package com.surkhojb.architectmovies.ui.favorite

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
import com.surkhojb.architectmovies.databinding.FragmentFavoritesBinding
import com.surkhojb.architectmovies.databinding.FragmentNewestBinding
import com.surkhojb.architectmovies.ui.common.EventObserver
import com.surkhojb.architectmovies.ui.common.OnLoadMoreItems
import com.surkhojb.architectmovies.ui.top_rated.adapter.MovieAdapter
import com.surkhojb.architectmovies.ui.top_rated.adapter.MoviewClickListener
import com.surkhojb.architectmovies.utils.getViewModel
import com.surkhojb.data.repositories.MoviesRepository
import com.surkhojb.data.repositories.RegionRepository
import com.surkhojb.domain.Movie
import com.surkhojb.usecases.GetFavorites
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.android.synthetic.main.fragment_newest.*


class FavoritesFragment : Fragment(){
    private lateinit var binding: FragmentFavoritesBinding
    lateinit var movieAdapter: MovieAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_favorites,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = getViewModel { FavoriteViewModel(
            GetFavorites(
                MoviesRepository(
                        RoomDataSource(),
                        DataStoreDataSource(),
                        TMDBDataSource(),
                        RegionRepository(
                                PlayServicesDataSource(),
                                PermissionManager()
                        )
                ))
        )}

        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this@FavoritesFragment

        configureView()

        viewModel.navigate.observe(viewLifecycleOwner, EventObserver { movie ->
            val action = FavoritesFragmentDirections.actionToDetail(movie.id)
            findNavController().navigate(action)
        })
    }

    private fun configureView(){
        movieAdapter = MovieAdapter()

        list_favorites.adapter = movieAdapter

        movieAdapter.addClickListener(object : MoviewClickListener {
            override fun onMovieClicked(movie: Movie) {
                viewModel.goToDetail(movie)
            }
        })
    }
}