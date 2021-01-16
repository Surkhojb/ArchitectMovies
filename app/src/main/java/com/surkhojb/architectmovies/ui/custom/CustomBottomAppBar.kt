package com.surkhojb.architectmovies.ui.custom

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.bottomnavigation.BottomNavigationView

class CustomBottomView(context: Context, attrs: AttributeSet): BottomNavigationView(context, attrs) {

    //Workaround to avoid shadow of BottomView over BottomAppBar
    init {
        this.background = null
    }
}