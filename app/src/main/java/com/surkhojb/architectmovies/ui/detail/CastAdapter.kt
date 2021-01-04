package com.surkhojb.architectmovies.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.surkhojb.architectmovies.R
import com.surkhojb.architectmovies.model.Cast
import com.surkhojb.architectmovies.utils.loadFromUrl

class CastAdapter: RecyclerView.Adapter<CastAdapter.CastViewHolder>() {
    private var cast: List<Cast>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_detail_cast_item,parent,false)
        return CastViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cast?.count() ?: 0
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val movie = cast?.get(position)
        movie?.let {
            holder.bind(it)
        }
    }

    fun refreshCast(updatedCast: List<Cast>){
        if (updatedCast.isNullOrEmpty())
            return

        cast = updatedCast
        notifyDataSetChanged()
    }

    inner class CastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val itemImage: ImageView = itemView.findViewById(R.id.item_image)
        private val itemName: TextView = itemView.findViewById(R.id.item_name)

        fun bind(cast: Cast){
            cast.profile_path?.let {
                itemImage.loadFromUrl(thumbnail = cast.profile_path)
            }
            itemName.text = cast.original_name
        }

    }
}