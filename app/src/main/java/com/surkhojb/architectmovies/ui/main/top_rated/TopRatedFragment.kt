package com.surkhojb.architectmovies.ui.main.top_rated

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.surkhojb.architectmovies.MainApp
import com.surkhojb.architectmovies.R
import com.surkhojb.architectmovies.databinding.FragmentTopRatedBinding
import com.surkhojb.architectmovies.ui.MainActivity
import com.surkhojb.architectmovies.ui.common.EventObserver
import com.surkhojb.architectmovies.ui.common.OnLoadMoreItems
import com.surkhojb.architectmovies.ui.main.MainActivityComponent
import com.surkhojb.architectmovies.ui.main.MainActivityModule
import com.surkhojb.architectmovies.ui.main.top_rated.adapter.MovieAdapter
import com.surkhojb.architectmovies.ui.main.top_rated.adapter.MoviewClickListener
import com.surkhojb.architectmovies.utils.getViewModel
import com.surkhojb.domain.Movie
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_top_rated.*


class TopRatedFragment : Fragment(){
    private lateinit var binding: FragmentTopRatedBinding
    lateinit var movieAdapter: MovieAdapter
    private lateinit var component: MainActivityComponent
    private val viewModel: TopRatedViewModel by lazy { getViewModel { component.topRatedViewModel } }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_top_rated,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        component = MainApp.component.plus((activity as MainActivity).mainActivityModule)

        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this@TopRatedFragment

        configureView()

        viewModel.navigate.observe(viewLifecycleOwner, EventObserver { movie ->
            val action = TopRatedFragmentDirections.actionToDetail(movie.id)
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