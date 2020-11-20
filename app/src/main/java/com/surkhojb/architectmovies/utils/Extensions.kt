package com.surkhojb.architectmovies.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
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