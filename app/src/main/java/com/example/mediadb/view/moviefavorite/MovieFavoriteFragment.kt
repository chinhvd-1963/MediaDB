package com.example.mediadb.view.moviefavorite


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mediadb.R
import com.example.mediadb.base.view.BaseFragment
import com.example.mediadb.data.model.dataresponse.Movie
import com.example.mediadb.databinding.MovieFavoriteFragmentBinding
import com.example.mediadb.view.moviedetail.MovieDetailFragment
import com.example.mediadb.view.movielist.MovieListAdapter
import com.example.mediadb.view.movielist.MovieListViewModel
import kotlinx.android.synthetic.main.movie_favorite_fragment.*
import kotlinx.android.synthetic.main.movie_list_fragment.*

class MovieFavoriteFragment : BaseFragment() {

    private lateinit var viewModel: MovieListViewModel
    private lateinit var movieFavoriteAdapter: MovieListAdapter
    private lateinit var binding: MovieFavoriteFragmentBinding

    companion object {
        fun newInstance() = MovieFavoriteFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.movie_favorite_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun initViewModel() {
        if (activity != null) {
            viewModel = ViewModelProviders.of(activity!!).get(MovieListViewModel::class.java)

            viewModel.showListMovieData().observe(viewLifecycleOwner, Observer {
                movieFavoriteAdapter.setAllMovieItems(it)
            })
        }
    }

    override fun onViewReady(view: View) {
        //Setting up RecyclerView.
        movieFavoriteAdapter = MovieListAdapter { movieItem: Movie -> movieItemClicked(movieItem) }
        val layoutManager = LinearLayoutManager(requireContext())
        rv_movie_favorite.apply {
            this.layoutManager = layoutManager
            hasFixedSize()
            addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
            adapter = movieFavoriteAdapter
        }

        // Todo: Get favorite movie data from database.
        viewModel.getListMovieData()
    }

    private fun movieItemClicked(movieItem: Movie) {
        viewModel.setSelectedMovie(movieItem)

        val movieDetailFragment = MovieDetailFragment.newInstance()
        if (activity != null) {
            activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_fragment, movieDetailFragment)
                .addToBackStack(MovieDetailFragment::class.java.simpleName)
                .commit()
        }
    }
}
