package com.surkhojb.architectmovies.ui.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Job

abstract class BaseActivity: AppCompatActivity(),CustomScope {
    override lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initScope()
    }

    override fun onDestroy() {
        clearScope()
        super.onDestroy()
    }
}