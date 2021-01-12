package com.surkhojb.architectmovies.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.surkhojb.architectmovies.R
import com.surkhojb.architectmovies.ui.common.DiffCallback
import com.surkhojb.architectmovies.utils.loadFromUrl
import com.surkhojb.domain.Movie
import java.util.*

interface MoviewClickListener{
    fun onMovieClicked(movie: Movie)
}

class MovieAdapter: RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private var movies: ArrayList<Movie> = arrayListOf()
    private var clickListener: MoviewClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item,parent,false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
       return movies.count()
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    fun refreshMovies(moreMovies: List<Movie>){
        val diffResult = DiffUtil.calculateDiff(DiffCallback(this.movies.toList(), moreMovies))
        movies.addAll(moreMovies)
        diffResult.dispatchUpdatesTo(this)
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
          val movie = movies[adapterPosition]
           movie.let {
               clickListener?.onMovieClicked(movie)
           }
       }

   }
}