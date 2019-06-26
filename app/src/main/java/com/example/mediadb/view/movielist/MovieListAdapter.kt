package com.example.mediadb.view.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mediadb.R
import com.example.mediadb.data.model.dataresponse.Movie
import com.example.mediadb.databinding.ItemMovieBinding
import java.util.*

class MovieListAdapter (val clickListener: (Movie) -> Unit) : RecyclerView.Adapter<MovieListAdapter.ViewHoler>() {

    private var movieList: MutableList<Movie> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoler {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemMovieBinding = DataBindingUtil.inflate(inflater,
            R.layout.item_movie, parent, false)
        return ViewHoler(binding)
    }

    override fun getItemCount(): Int {
        return movieList.count()
    }

    fun setAllMovieItems(movieItems: MutableList<Movie>) {
        this.movieList = movieItems
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHoler, position: Int) {
        holder.bind(movieList[position], clickListener)
    }

    inner class ViewHoler(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, clickListener: (Movie) -> Unit) {
            binding.item = movie

            itemView.setOnClickListener { clickListener(movie) }
            binding.executePendingBindings()
        }
    }
}