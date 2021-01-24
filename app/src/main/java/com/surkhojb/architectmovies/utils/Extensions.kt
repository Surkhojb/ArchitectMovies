
package com.surkhojb.architectmovies.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.surkhojb.architectmovies.R

const val THUMBNAIL_BASE_URL = "https://image.tmdb.org/t/p/w185/"
const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w1280/"

enum class ThumbnailType{
    POSTER,
    THUMBNAIL
}

fun ImageView.loadFromUrl(type: ThumbnailType? = ThumbnailType.THUMBNAIL, thumbnail: String?){R.drawable.ic_no_thumbnail
    when(type){
        ThumbnailType.THUMBNAIL -> {Glide.with(context).load(THUMBNAIL_BASE_URL + thumbnail).into(this)}
        ThumbnailType.POSTER -> {Glide.with(context).load(POSTER_BASE_URL + thumbnail).into(this)}
    }

}

inline fun <reified T: Activity> Activity.launchActivity(){
    startActivity(Intent(this,T::class.java))
}

inline fun <reified T: Activity> Activity.launchActivity(extras: Bundle){
    val intent = Intent(this,T::class.java).apply {
       putExtras(extras)
    }
    startActivity(intent)
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> FragmentActivity.getViewModel(crossinline factory: () -> T): T {

    val vmFactory = object : ViewModelProvider.Factory {
        override fun <U : ViewModel> create(modelClass: Class<U>): U = factory() as U
    }

    return ViewModelProvider(this, vmFactory).get(T::class.java)
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> Fragment.getViewModel(crossinline factory: () -> T): T {

    val vmFactory = object : ViewModelProvider.Factory {
        override fun <U : ViewModel> create(modelClass: Class<U>): U = factory() as U
    }

    return ViewModelProvider(this, vmFactory).get(T::class.java)
}

fun Activity.navigateTo(view: Int, destination: Int): Boolean {
    this.actionBar?.title = "Title"
    this.findNavController(view).navigate(destination)
    return true
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

@Suppress("DEPRECATION")
fun View.openKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInputFromInputMethod(windowToken, 0)
}