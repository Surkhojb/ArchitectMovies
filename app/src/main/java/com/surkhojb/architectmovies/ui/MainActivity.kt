package com.surkhojb.architectmovies.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.surkhojb.architectmovies.R
import com.surkhojb.architectmovies.ui.main.MainActivityModule
import com.surkhojb.architectmovies.utils.navigateTo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var  mainActivityModule: MainActivityModule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActivityModule = MainActivityModule()
        
        bottom_view.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_top_rated -> navigateTo(R.id.nav_host_fragment_container,R.id.topRated)
                R.id.menu_newest -> navigateTo(R.id.nav_host_fragment_container,R.id.newest)
                R.id.menu_favorite -> navigateTo(R.id.nav_host_fragment_container,R.id.favorites)
                else -> false
            }
        }

        fab.setOnClickListener {
            fab.hide()
            navigateTo(R.id.nav_host_fragment_container,R.id.search)
        }
    }
}