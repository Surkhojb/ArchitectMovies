package com.surkhojb.architectmovies.ui.main.search

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.surkhojb.architectmovies.R
import com.surkhojb.architectmovies.databinding.FragmentSearchBinding
import com.surkhojb.architectmovies.ui.MainActivity
import com.surkhojb.architectmovies.ui.common.EventObserver
import com.surkhojb.architectmovies.ui.main.top_rated.adapter.MovieAdapter
import com.surkhojb.architectmovies.ui.main.top_rated.adapter.MoviewClickListener
import com.surkhojb.domain.Movie
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : ScopeFragment() {

    private lateinit var binding: FragmentSearchBinding
    lateinit var movieAdapter: MovieAdapter
    private val viewModel: SearchViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val activity = (activity as MainActivity)
        activity.supportActionBar?.title = getString(R.string.search_title)

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_search,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this@SearchFragment

        configureView()

        viewModel.navigate.observe(viewLifecycleOwner, EventObserver { movie ->
            val action = SearchFragmentDirections.actionToDetail(movie.id)
            findNavController().navigate(action)
        })

        viewModel.searchs.observe(viewLifecycleOwner, Observer {
            buildChips(it)
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

    private fun buildChips(list: List<String>){
        if(list.isNullOrEmpty()) return

        list.forEach { item ->
            binding.chipGroup.addView(Chip(requireContext()).apply {
                text = item
                chipIcon = ContextCompat.getDrawable(context,R.drawable.ic_fab_search)
                chipIconTint = ColorStateList.valueOf(Color.BLACK)
                isClickable = true
                setOnClickListener { binding.searchView.setQuery(item,false) }
            })
            binding.chipGroup.visibility = View.VISIBLE
        }
    }

}