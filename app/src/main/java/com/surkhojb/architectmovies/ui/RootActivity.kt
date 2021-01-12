package com.surkhojb.architectmovies.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.surkhojb.architectmovies.R
import kotlinx.android.synthetic.main.activity_root.*

class RootActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        bottom_view.background = null
    }
}