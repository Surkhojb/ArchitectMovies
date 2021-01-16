package com.surkhojb.architectmovies.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.navArgs
import com.surkhojb.architectmovies.MainApp
import com.surkhojb.architectmovies.R
import com.surkhojb.architectmovies.databinding.ActivityDetailBinding
import com.surkhojb.architectmovies.utils.getViewModel
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val args: DetailActivityArgs by navArgs()
    private lateinit var component: DetailActivityComponent
    private val viewModel: DetailViewModel by lazy { getViewModel { component.detailViewModel } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail)

        component = MainApp.component.plus(DetailActivityModule())

        viewModel.movieId = args.movieId
        viewModel.loadDetail()

        binding.lifecycleOwner = this@DetailActivity
        binding.viewModel = this.viewModel

        list_cast.adapter = CastAdapter()
    }

}