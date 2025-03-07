package com.surkhojb.architectmovies.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.surkhojb.architectmovies.R
import com.surkhojb.architectmovies.utils.navigateTo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_app_bar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.menu_top_rated -> navigateTo(R.id.nav_host_fragment_container,R.id.topRated)
                R.id.menu_newest -> navigateTo(R.id.nav_host_fragment_container,R.id.newest)
                R.id.menu_favorite -> navigateTo(R.id.nav_host_fragment_container,R.id.favorites)
                else -> false
            }
        }

        fab.setOnClickListener {
            fab.hide()
            bottom_app_bar.performHide()
            supportActionBar?.title = getString(R.string.search_title)
            navigateTo(R.id.nav_host_fragment_container,R.id.search)
        }
    }
}