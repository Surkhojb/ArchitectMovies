package com.surkhojb.architectmovies.ui.main.newest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.surkhojb.architectmovies.R
import com.surkhojb.architectmovies.databinding.FragmentNewestBinding
import com.surkhojb.architectmovies.ui.MainActivity
import com.surkhojb.architectmovies.ui.common.EventObserver
import com.surkhojb.architectmovies.ui.common.OnLoadMoreItems
import com.surkhojb.architectmovies.ui.common.adapter.MovieAdapter
import com.surkhojb.architectmovies.ui.common.adapter.MoviewClickListener
import com.surkhojb.domain.Movie
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_newest.*
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewestFragment : ScopeFragment(){
    private lateinit var binding: FragmentNewestBinding
    lateinit var movieAdapter: MovieAdapter
    private val viewModel: NewestViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val activity = (activity as MainActivity)
        activity.supportActionBar?.title = getString(R.string.newest_title)

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_newest,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this@NewestFragment

        configureView()

        viewModel.navigate.observe(viewLifecycleOwner, EventObserver { movie ->
            val action = NewestFragmentDirections.actionToDetail(movie.id)
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