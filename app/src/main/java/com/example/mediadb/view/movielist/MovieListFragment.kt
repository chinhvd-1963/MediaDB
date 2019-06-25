package com.example.mediadb.view.movielist

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
import com.example.mediadb.base.Logger
import com.example.mediadb.base.view.BaseFragment
import com.example.mediadb.data.model.dataresponse.Movie
import com.example.mediadb.databinding.MovieListFragmentBinding
import kotlinx.android.synthetic.main.movie_list_fragment.*

class MovieListFragment : BaseFragment() {

    private val TAG = MovieListFragment::class.java.simpleName

    private lateinit var viewModel: MovieListViewModel
    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var binding: MovieListFragmentBinding

    companion object {
        fun newInstance(): MovieListFragment {
            val fragment = MovieListFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.movie_list_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(activity!!).get(MovieListViewModel::class.java)

        viewModel.showListMovieData().observe(viewLifecycleOwner, Observer {
            movieListAdapter.setAllMovieItems(it)
        })
    }

    override fun onViewReady(view: View) {
        //Setting up RecyclerView.
        val layoutManager = LinearLayoutManager(requireContext())
        rv_movie_list.layoutManager = layoutManager
        rv_movie_list.hasFixedSize()
        rv_movie_list.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
        movieListAdapter = MovieListAdapter { movieItem: Movie -> movieItemClicked(movieItem) }
        rv_movie_list.adapter = movieListAdapter

        // Get movie data from service.
        viewModel.getListMovieData()
    }

    private fun movieItemClicked(movieItem: Movie) {
        //Todo: Implement handler item click.
    }
}
