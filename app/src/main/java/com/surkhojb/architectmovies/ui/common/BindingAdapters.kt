package com.surkhojb.architectmovies.ui.common

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.surkhojb.architectmovies.utils.loadFromUrl

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

@BindingAdapter("textFromDouble")
fun TextView.text(value: Double?) {
   text = value.toString()
}

@BindingAdapter("textFromInt")
fun TextView.text(value: Int?) {
    text = value.toString()
}