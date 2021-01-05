package com.surkhojb.architectmovies.ui.detail

import android.widget.TextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.surkhojb.architectmovies.data.local.model.Cast
import com.surkhojb.architectmovies.data.local.model.Movie
import com.surkhojb.architectmovies.ui.main.adapter.MovieAdapter
import java.util.*

@Suppress("UNCHECKED_CAST")
@BindingAdapter("items")
fun <T: Any> RecyclerView.setItems(items: List<T>?){
    if(items?.get(0) is Movie){
        (adapter as MovieAdapter)?.let{
            it.refreshMovies(items as List<Movie>)
        }
    }

    if (items?.get(0) is Cast){
        (adapter as CastAdapter)?.let {
            it.refreshCast(items as List<Cast>)
        }
    }
}

@BindingAdapter("buildInfo")
fun TextView.text(movie: Movie?){
    movie?.let {
        text = buildSpannedString {
            bold { append("Original language: ") }
            appendLine(it.originalLanguage.toUpperCase(Locale.getDefault()))
            bold { append("Original title: ") }
            appendLine(it.originalTitle)
            bold { append("Release date: ") }
            appendLine(it.releaseDate)
        }
    }
}