package com.surkhojb.architectmovies.ui.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

interface CustomScope: CoroutineScope {
    var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun initScope(){
        job = SupervisorJob()
    }

    fun clearScope(){
        job.cancel()
    }
}