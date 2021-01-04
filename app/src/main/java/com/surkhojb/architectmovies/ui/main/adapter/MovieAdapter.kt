package com.surkhojb.architectmovies.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.surkhojb.architectmovies.R
import com.surkhojb.architectmovies.data.local.model.Movie
import com.surkhojb.architectmovies.utils.loadFromUrl
import java.lang.IllegalArgumentException

interface MoviewClickListener{
    fun onMovieClicked(movie: Movie)
}

class MovieAdapter: RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private var movies: List<Movie>? = null
    private var clickListener: MoviewClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item,parent,false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
       return movies?.count() ?: 0
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies?.get(position)
        movie?.let {
            holder.bind(it)
        }
    }

    fun refreshMovies(updatedMovies: List<Movie>){
        if (updatedMovies.isNullOrEmpty())
            return

        movies = updatedMovies
        notifyDataSetChanged()
    }

    fun addClickListener(listener: MoviewClickListener?){
        if(listener == null)
            throw IllegalArgumentException("Listener can't be null")

        clickListener = listener
    }

   inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val itemBackground: ImageView = itemView.findViewById(R.id.item_background)
        private val itemTitle: TextView = itemView.findViewById(R.id.item_title)

       fun bind(movie:Movie){
           itemBackground.loadFromUrl(thumbnail = movie.posterPath)
           itemTitle.text = movie.title
           itemView.setOnClickListener(this)
       }

       override fun onClick(p0: View?) {
          val movie = movies?.get(adapterPosition)
           movie?.let {
               clickListener?.onMovieClicked(movie)
           }
       }

   }
}