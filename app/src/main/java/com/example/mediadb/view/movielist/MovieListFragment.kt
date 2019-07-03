package com.example.mediadb.view.movielist

import android.os.Bundle
import android.util.Log
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
import com.example.mediadb.utils.Constants
import com.example.mediadb.view.moviedetail.MovieDetailFragment
import kotlinx.android.synthetic.main.movie_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MovieListFragment : BaseFragment() {

    private val TAG = MovieListFragment::class.java.simpleName

    private val viewModel: MovieListViewModel by sharedViewModel()
    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var binding: MovieListFragmentBinding
    private var endlessRecyclerOnScrollListener = object : EndlessRecyclerOnScrollListener() {
        override fun onLoadMore() {
            viewModel.endlessLoading()
        }
    }

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
        //initViewModel of Data binding.
        binding.viewModel = viewModel

        setObserveEvent(viewModel)
        viewModel.listMovieData.observe(viewLifecycleOwner, Observer {
            viewModel.movieListApi.addAll(it)
            movieListAdapter.setAllMovieItems(viewModel.movieListApi)
            swipe_refresh_list_movie.isRefreshing = false
        })
        viewModel.isListMovieInitialized.observe(viewLifecycleOwner, Observer {
            if (!it) {
                // Get movie data from service.
                viewModel.getListMovieData(MovieListViewModel.DEFAULT_PAGE_NUMBER)
                // Visiable progress bar loading.
                viewModel.isMovieListLoading.value = true
                //Initialize list movie value only one time.
                viewModel.saveListMovieInitialized()
            }
        })
        viewModel.isMovieListLoading.observe(viewLifecycleOwner, Observer {
            swipe_refresh_list_movie.isRefreshing = false
        })
    }

    override fun onViewReady(view: View) {
        //Setting up RecyclerView.
        movieListAdapter =
            MovieListAdapter { movieItem: Movie -> movieItemClicked(movieItem) }
        val layoutManager = GridLayoutManager(requireContext(), Constants.NUMBER_COLUMNS_RECYCLE)
        rv_movie_list.apply {
            this.layoutManager = layoutManager
            hasFixedSize()
            addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
            adapter = movieListAdapter
        }
        movieListAdapter.setAllMovieItems(viewModel.movieListApi)

        //Swipe to refresh list movie
        swipe_refresh_list_movie.setOnRefreshListener {
            viewModel.movieListApi.clear()

            viewModel.nextPage.value = MovieListViewModel.DEFAULT_PAGE_NUMBER

            endlessRecyclerOnScrollListener.mPreviousTotal = Constants.DEFAULT_NUMBER_PREVIEW_MOVIE_ITEM
            endlessRecyclerOnScrollListener.mLoading = true

            viewModel.getListMovieData(MovieListViewModel.DEFAULT_PAGE_NUMBER)
        }

        initScrollListener()
    }

    private fun initScrollListener() {
        rv_movie_list.addOnScrollListener(endlessRecyclerOnScrollListener)
    }

    private fun movieItemClicked(movieItem: Movie) {
        viewModel.movieItem.value = movieItem

        val movieDetailFragment = MovieDetailFragment.newInstance()
        if (activity != null) {
            viewModel.isHasNavigation.value = false
            activity!!.supportFragmentManager
                .beginTransaction()
                .add(R.id.container_fragment, movieDetailFragment)
                .addToBackStack(MovieDetailFragment::class.java.simpleName)
                .commit()
        }
    }
}
