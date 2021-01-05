package com.surkhojb.architectmovies.ui.detail

import android.os.Bundle
import android.view.View.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.surkhojb.architectmovies.R
import com.surkhojb.architectmovies.data.local.model.Movie
import com.surkhojb.architectmovies.data.repository.MoviesRepository
import com.surkhojb.architectmovies.databinding.ActivityDetailBinding
import com.surkhojb.architectmovies.utils.ThumbnailType
import com.surkhojb.architectmovies.utils.getViewModel
import com.surkhojb.architectmovies.utils.loadFromUrl
import kotlinx.android.synthetic.main.activity_detail.*
import java.util.*

const val ITEM_KEY = "item"

class DetailActivity : AppCompatActivity() {
    lateinit var castList: RecyclerView
    lateinit var castAdapter: CastAdapter
    lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =  getViewModel { DetailViewModel(MoviesRepository()) }

        val binding : ActivityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val movieId = intent.getIntExtra(ITEM_KEY,-1)
        configureView()

        viewModel.loadMovie(movieId)
        viewModel.loadCast(movieId)
    }

    private fun configureView(){
        castList = findViewById(R.id.list_cast)
        castList.hasFixedSize()
        castAdapter = CastAdapter()
        castList.adapter = castAdapter
    }

}