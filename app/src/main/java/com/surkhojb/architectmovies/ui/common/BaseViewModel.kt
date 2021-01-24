package com.surkhojb.architectmovies.ui.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job

open class BaseViewModel(uiDispatcher: CoroutineDispatcher): ViewModel(), CustomScope {

    override lateinit var job: Job
    override var dispatcher: CoroutineDispatcher = uiDispatcher

    init {
        initScope()
    }

    override fun clearScope() {
        clearScope()
    }
}