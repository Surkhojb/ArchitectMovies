package com.surkhojb.architectmovies.ui.common

import androidx.recyclerview.widget.DiffUtil
import com.surkhojb.architectmovies.data.local.model.Movie

class DiffCallback(private val oldItems: List<Movie>, private val newItems: List<Movie>): DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newItems[newItemPosition].id == oldItems[oldItemPosition].id
    }

    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
       return newItems.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newItems[newItemPosition] == oldItems[oldItemPosition]
    }
}