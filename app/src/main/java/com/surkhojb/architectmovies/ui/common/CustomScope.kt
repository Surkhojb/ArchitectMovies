package com.surkhojb.architectmovies.ui.common

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

interface CustomScope: CoroutineScope {
    var job: Job
    var dispatcher: CoroutineDispatcher

    override val coroutineContext: CoroutineContext
        get() = dispatcher + job

    fun initScope(){
        job = SupervisorJob()
    }

    fun clearScope(){
        job.cancel()
    }
}