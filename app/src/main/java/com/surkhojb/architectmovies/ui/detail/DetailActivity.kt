package com.surkhojb.architectmovies.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.navArgs
import com.surkhojb.architectmovies.R
import com.surkhojb.architectmovies.databinding.ActivityDetailBinding
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val args: DetailActivityArgs by navArgs()
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail)

        viewModel.movieId = args.movieId
        viewModel.loadDetail()

        binding.lifecycleOwner = this@DetailActivity
        binding.viewModel = this.viewModel

        list_cast.adapter = CastAdapter()
    }

}