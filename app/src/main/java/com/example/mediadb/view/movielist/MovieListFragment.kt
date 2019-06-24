package com.example.mediadb.view.movielist

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mediadb.R
import com.example.mediadb.base.view.BaseFragment
import kotlinx.android.synthetic.main.movie_list_fragment.*

class MovieListFragment : BaseFragment() {

    private val TAG = MovieListFragment::class.java.simpleName

    private lateinit var viewModel: MovieListViewModel
    private lateinit var movieListAdapter: MovieListAdapter

    companion object {
        fun newInstance(): MovieListFragment {
            val fragment = MovieListFragment()
            return fragment
        }
    }

    override fun getLayoutID(): Int {
        return R.layout.movie_list_fragment
    }

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(activity!!).get(MovieListViewModel::class.java)

        viewModel.showListMovieData().observe(this, Observer {
            //            Logger.d(TAG, "showListMovieData() ${it[1].title}")
            movieListAdapter.setAllMovieItems(it)
        })
    }

    override fun onViewReady(view: View) {
        //Setting up RecyclerView.
        rv_movie_list.layoutManager = LinearLayoutManager(requireContext())
        movieListAdapter = MovieListAdapter()
        rv_movie_list.adapter = movieListAdapter

        // Get movie data from service.
        viewModel.getListMovieData()
    }

}
