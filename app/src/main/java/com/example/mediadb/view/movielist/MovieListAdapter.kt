package com.example.mediadb.view.movielist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mediadb.R
import com.example.mediadb.data.model.dataresponse.Movie
import kotlinx.android.synthetic.main.item_movie.view.*
import java.util.*

class MovieListAdapter : RecyclerView.Adapter<MovieListAdapter.ViewHoler>() {

    private var movieList: MutableList<Movie> = ArrayList()
    private var listener: MovieListEvent? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoler {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHoler(view)
    }

    override fun getItemCount(): Int {
        return movieList.count()
    }

    fun setEventListenner(listenner: MovieListEvent) {
        this.listener = listenner
    }

    fun setAllMovieItems(movieItems: MutableList<Movie>) {
        this.movieList = movieItems
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHoler, position: Int) {
        holder.bind(movieList[position], listener)
    }

    inner class ViewHoler(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie, listener: MovieListEvent?) {

            itemView.tv_movie_title.text = movie.title

            itemView.setOnClickListener {
                listener!!.onItemClicked(movie)
            }
        }
    }

    interface MovieListEvent {
        fun onItemClicked(movie: Movie)
    }
}