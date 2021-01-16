package com.surkhojb.architectmovies.ui.main.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.surkhojb.architectmovies.R
import com.surkhojb.architectmovies.databinding.FragmentFavoritesBinding
import com.surkhojb.architectmovies.ui.MainActivity
import com.surkhojb.architectmovies.ui.common.EventObserver
import com.surkhojb.architectmovies.ui.main.top_rated.adapter.MovieAdapter
import com.surkhojb.architectmovies.ui.main.top_rated.adapter.MoviewClickListener
import com.surkhojb.domain.Movie
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_favorites.*
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoritesFragment : ScopeFragment(){
    private lateinit var binding: FragmentFavoritesBinding
    lateinit var movieAdapter: MovieAdapter
    private val viewModel: FavoriteViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_favorites,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this@FavoritesFragment

        configureView()

        viewModel.navigate.observe(viewLifecycleOwner, EventObserver { movie ->
            val action = FavoritesFragmentDirections.actionToDetail(movie.id)
            findNavController().navigate(action)
        })
    }

    override fun onResume() {
        super.onResume()
        val activity = (activity as MainActivity)
        if(activity.fab.visibility == View.GONE){
            activity.fab.show()
        }
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