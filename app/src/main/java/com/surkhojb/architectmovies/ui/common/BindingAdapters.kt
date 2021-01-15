package com.surkhojb.architectmovies.ui.common

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.surkhojb.architectmovies.ui.detail.CastAdapter
import com.surkhojb.architectmovies.ui.top_rated.adapter.MovieAdapter
import com.surkhojb.architectmovies.utils.ThumbnailType
import com.surkhojb.architectmovies.utils.loadFromUrl
import com.surkhojb.domain.Cast
import com.surkhojb.domain.Movie

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean?) {
    visibility = visible?.let {
        if (visible) View.VISIBLE else View.GONE
    } ?: View.GONE
}

@BindingAdapter("url")
fun ImageView.bindUrl(url: String?) {
    if (url != null) loadFromUrl(thumbnail = url)
}

@BindingAdapter("urlPoster")
fun ImageView.bindUrlPoster(url: String?) {
    if (url != null) loadFromUrl(thumbnail = url, type = ThumbnailType.POSTER)
}

@BindingAdapter("textFromDouble")
fun TextView.text(value: Double?) {
   text = value.toString()
}

@BindingAdapter("textFromInt")
fun TextView.text(value: Int?) {
    text = value.toString()
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("items")
fun <T: Any> RecyclerView.setItems(items: List<T>?){
    if(items?.size == 0)
        return

    if(items?.get(0) is Movie){
        (adapter as MovieAdapter)?.let{
            it.refreshMovies(items as List<Movie>)
            adapter?.notifyDataSetChanged()
        }
    }

    if (items?.get(0) is Cast){
        (adapter as CastAdapter)?.let {
            it.refreshCast(items as List<Cast>)
            adapter?.notifyDataSetChanged()
        }
    }
}