package com.surkhojb.architectmovies.ui.common

import android.text.Layout
import android.util.Log
import androidx.recyclerview.widget.RecyclerView

abstract class OnLoadMoreItems: RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if(dy > 0 && !recyclerView.canScrollVertically(1)) {
            loadMoreItems()
            Log.d("RecyclerView", "LoadMoreItems")
        }

    }

    abstract fun loadMoreItems()
}