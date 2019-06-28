package com.example.mediadb.view.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mediadb.R
import com.example.mediadb.base.view.BaseFragment
import com.example.mediadb.data.model.dataresponse.Movie
import com.example.mediadb.databinding.MovieListFragmentBinding
import com.example.mediadb.view.moviedetail.MovieDetailFragment
import kotlinx.android.synthetic.main.movie_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MovieListFragment : BaseFragment() {

    private val TAG = MovieListFragment::class.java.simpleName

    private val viewModel: MovieListViewModel by sharedViewModel()
    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var binding: MovieListFragmentBinding

    companion object {
        fun newInstance(): MovieListFragment {
            return MovieListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.movie_list_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun initViewModel() {
        setObserveEvent(viewModel)
        viewModel.listMovieData.observe(viewLifecycleOwner, Observer {
            movieListAdapter.setAllMovieItems(it)
        })
    }

    override fun onViewReady(view: View) {
        //Setting up RecyclerView.
        movieListAdapter = MovieListAdapter { movieItem: Movie -> movieItemClicked(movieItem) }
        val layoutManager = LinearLayoutManager(requireContext())
        rv_movie_list.apply {
            this.layoutManager = layoutManager
            hasFixedSize()
            addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
            adapter = movieListAdapter
        }

        // Get movie data from service.
        viewModel.getListMovieData()
    }

    private fun movieItemClicked(movieItem: Movie) {
        viewModel.movieItem.value = movieItem

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
