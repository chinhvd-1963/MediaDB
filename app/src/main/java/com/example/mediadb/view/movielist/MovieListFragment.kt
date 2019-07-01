package com.example.mediadb.view.movielist

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
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
    private var movieListApi: MutableList<Movie> = ArrayList()

    private var loadedPage = MovieListViewModel.DEFAULT_PAGE_NUMBER

    companion object {
        fun newInstance(): MovieListFragment {
            return MovieListFragment()
        }

        const val NUMBER_COLUMNS_RECYCLE = 2
        const val ENDLESS_LOADING_TIME = 2000L
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.movie_list_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun initViewModel() {
        setObserveEvent(viewModel)
        viewModel.listMovieData.observe(viewLifecycleOwner, Observer {
            movieListApi.addAll(it)
            movieListAdapter.setAllMovieItems(movieListApi)
        })
    }

    override fun onViewReady(view: View) {
        //Setting up RecyclerView.
        movieListAdapter =
            MovieListAdapter { movieItem: Movie -> movieItemClicked(movieItem) }
        val layoutManager = GridLayoutManager(requireContext(), NUMBER_COLUMNS_RECYCLE)
        rv_movie_list.apply {
            this.layoutManager = layoutManager
            hasFixedSize()
            addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
            adapter = movieListAdapter
        }

        initScrollListener()

        // Get movie data from service.
        viewModel.getListMovieData(MovieListViewModel.DEFAULT_PAGE_NUMBER)

        //Swipe to refresh list movie
        swipe_refresh_list_movie.setOnRefreshListener {
            movieListApi.clear()
            viewModel.getListMovieData(MovieListViewModel.DEFAULT_PAGE_NUMBER)
            swipe_refresh_list_movie.isRefreshing = false
        }
    }

    private fun initScrollListener() {
        rv_movie_list.addOnScrollListener(object : EndlessRecyclerOnScrollListener() {
            override fun onLoadMore() {
                endlessLoading()
            }

        })
    }

    private fun endlessLoading() {
        progressBar.visibility = View.VISIBLE

        Handler().postDelayed({
            loadedPage++
            viewModel.getListMovieData(loadedPage)

            progressBar.visibility = View.GONE
        }, ENDLESS_LOADING_TIME)
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
