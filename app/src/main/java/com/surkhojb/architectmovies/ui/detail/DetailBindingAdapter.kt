package com.surkhojb.architectmovies.ui.detail

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.surkhojb.architectmovies.R
import com.surkhojb.architectmovies.data.local.model.Movie
import java.util.*

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

@BindingAdapter("isFavorite")
fun FloatingActionButton.image(favorite: Boolean?){
    val icon = if (favorite == true) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off
    setImageDrawable(ContextCompat.getDrawable(context,icon))
}