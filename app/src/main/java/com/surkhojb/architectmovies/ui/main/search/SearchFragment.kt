package com.surkhojb.architectmovies.ui.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.surkhojb.architectmovies.MainApp
import com.surkhojb.architectmovies.R
import com.surkhojb.architectmovies.databinding.FragmentSearchBinding
import com.surkhojb.architectmovies.ui.MainActivity
import com.surkhojb.architectmovies.ui.common.EventObserver
import com.surkhojb.architectmovies.ui.main.MainActivityComponent
import com.surkhojb.architectmovies.ui.main.MainActivityModule
import com.surkhojb.architectmovies.ui.main.top_rated.adapter.MovieAdapter
import com.surkhojb.architectmovies.ui.main.top_rated.adapter.MoviewClickListener
import com.surkhojb.architectmovies.utils.getViewModel
import com.surkhojb.domain.Movie
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    lateinit var movieAdapter: MovieAdapter
    private lateinit var component: MainActivityComponent
    private val viewModel: SearchViewModel by lazy { getViewModel { component.searchViewModel } }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val activity = (activity as MainActivity)
        activity.supportActionBar?.title = getString(R.string.search_title)

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_search,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        component = MainApp.component.plus((activity as MainActivity).mainActivityModule)

        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this@SearchFragment

        configureView()

        viewModel.navigate.observe(viewLifecycleOwner, EventObserver { movie ->
            val action = SearchFragmentDirections.actionToDetail(movie.id)
            findNavController().navigate(action)
        })

    }

    private fun configureView(){
        movieAdapter = MovieAdapter()

        list_search.adapter = movieAdapter

        movieAdapter.addClickListener(object : MoviewClickListener {
            override fun onMovieClicked(movie: Movie) {
                viewModel.goToDetail(movie)
            }
        })

        search_view.apply {
            queryHint = "Search a movie."
            isIconified = false
            requestFocus()
            callOnClick()
        }

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchMovie(query.toString())
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })
    }

}