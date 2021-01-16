package com.surkhojb.architectmovies.di

import android.app.Application
import com.surkhojb.architectmovies.ui.detail.DetailActivityComponent
import com.surkhojb.architectmovies.ui.detail.DetailActivityModule
import com.surkhojb.architectmovies.ui.main.MainActivityComponent
import com.surkhojb.architectmovies.ui.main.MainActivityModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(AppModule::class, DataModule::class))
interface MainAppComponent {

    fun plus(module: MainActivityModule): MainActivityComponent
    fun plus(module: DetailActivityModule): DetailActivityComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): MainAppComponent
    }
}