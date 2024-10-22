package com.surkhojb.architectmovies.ui.main.search

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.surkhojb.architectmovies.R
import com.surkhojb.architectmovies.databinding.FragmentSearchBinding
import com.surkhojb.architectmovies.ui.MainActivity
import com.surkhojb.architectmovies.ui.common.EventObserver
import com.surkhojb.architectmovies.utils.hideKeyboard
import com.surkhojb.architectmovies.utils.openKeyboard
import com.surkhojb.domain.Movie
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : ScopeFragment() {

    private lateinit var binding: FragmentSearchBinding
    lateinit var movieAdapter: SearchMovieAdapter
    private val viewModel: SearchViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        setHasOptionsMenu(true)
        setMenuVisibility(false)

        val activity = (activity as MainActivity)
        activity.supportActionBar?.title = getString(R.string.search_title)

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_search,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this@SearchFragment

        configureView()

        viewModel.navigate.observe(viewLifecycleOwner, EventObserver { movie ->
            val action = SearchFragmentDirections.actionToDetail(movie.id)
            findNavController().navigate(action)
        })

        viewModel.movies.observe(viewLifecycleOwner, Observer {
            movieAdapter.refreshMovies(it)
        })

        viewModel.searchs.observe(viewLifecycleOwner, Observer {
            buildChips(it)
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search_fragment,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_search -> {
                animateSearch(hide = false)
            }
        }

        return true
    }

    private fun configureView(){
        movieAdapter = SearchMovieAdapter()

        list_search.adapter = movieAdapter

        movieAdapter.addClickListener(object : MoviewClickListener {
            override fun onMovieClicked(movie: Movie) {
                viewModel.goToDetail(movie)
            }
        })

        searchViewBehaviour()

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                animateSearch(hide = true)
                viewModel.searchMovie(query.toString())
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })

        search_view.apply {
            setOnCloseListener {
                animateSearch(hide = true)
                true
            }
        }
    }

    private fun buildChips(list: List<String>){
        if(list.isNullOrEmpty()) return

        binding.chipGroup.removeAllViews()

        list.forEach { item ->
            binding.chipGroup.addView(Chip(requireContext()).apply {
                text = item
                textSize = 12f
                chipIcon = ContextCompat.getDrawable(context,R.drawable.ic_fab_search)
                chipIconTint = ColorStateList.valueOf(Color.BLACK)
                isClickable = true
                setOnClickListener { binding.searchView.setQuery(item,true) }
            })
            binding.chipGroup.visibility = View.VISIBLE
        }
    }

    private fun animateSearch(hide: Boolean){
        when(hide){
            true -> {
                cv_search.animate().translationY(-cv_search.height.toFloat() * 2)
                search_view.hideKeyboard()
                setMenuVisibility(true)
            }
            false -> {
                cv_search.animate().translationY(0f)
                setMenuVisibility(false)
                searchViewBehaviour()
            }
        }
    }

    private fun searchViewBehaviour() {
        search_view.apply {
            isIconified = false
            requestFocus()
            callOnClick()
            this.openKeyboard()
            queryHint = "Search a movie"
        }
    }

}