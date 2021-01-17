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
import androidx.navigation.fragment.findNavController
import com.surkhojb.architectmovies.MainApp
import com.google.android.material.chip.Chip
import com.surkhojb.architectmovies.R
import com.surkhojb.architectmovies.databinding.FragmentSearchBinding
import com.surkhojb.architectmovies.ui.MainActivity
import com.surkhojb.architectmovies.ui.common.EventObserver
import com.surkhojb.architectmovies.ui.main.MainActivityComponent
import com.surkhojb.architectmovies.utils.getViewModel
import com.surkhojb.domain.Movie
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    lateinit var movieAdapter: SearchMovieAdapter
    private lateinit var component: MainActivityComponent
    private val viewModel: SearchViewModel by lazy { getViewModel { component.searchViewModel } }

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
        component = MainApp.component.plus((activity as MainActivity).mainActivityModule)

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

        search_view.apply {
            queryHint = "Search a movie."
            isIconified = false
            requestFocus()
            callOnClick()
        }

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
    }

    private fun buildChips(list: List<String>){
        if(list.isNullOrEmpty()) return

        binding.chipGroup.removeAllViews()

        list.forEach { item ->
            binding.chipGroup.addView(Chip(requireContext()).apply {
                text = item
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
                setMenuVisibility(true)
            }
            false -> {
                cv_search.animate().translationY(0f)
                setMenuVisibility(false)
            }
        }
    }

}