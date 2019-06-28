package com.example.mediadb.view.moviefavorite


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mediadb.R
import com.example.mediadb.base.view.BaseFragment
import com.example.mediadb.data.model.dataresponse.Movie
import com.example.mediadb.databinding.MovieFavoriteFragmentBinding
import com.example.mediadb.view.moviedetail.MovieDetailFragment
import com.example.mediadb.view.movielist.MovieListAdapter
import com.example.mediadb.view.movielist.MovieListViewModel
import kotlinx.android.synthetic.main.movie_favorite_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MovieFavoriteFragment : BaseFragment() {

    private val viewModel: MovieListViewModel by sharedViewModel()
    private lateinit var movieFavoriteAdapter: MovieListAdapter
    private lateinit var binding: MovieFavoriteFragmentBinding

    companion object {
        fun newInstance() = MovieFavoriteFragment()
        const val NUMBER_COLUMNS_RECYCLE = 2
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.movie_favorite_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun initViewModel() {
        viewModel.listFavoriteMovie.observe(viewLifecycleOwner, Observer {
            movieFavoriteAdapter.setAllMovieItems(it)
        })
    }

    override fun onViewReady(view: View) {
        //Setting up RecyclerView.
        movieFavoriteAdapter = MovieListAdapter { movieItem: Movie -> movieItemClicked(movieItem) }
        val layoutManager = GridLayoutManager(requireContext(), NUMBER_COLUMNS_RECYCLE)
        rv_movie_favorite.apply {
            this.layoutManager = layoutManager
            hasFixedSize()
            addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
            adapter = movieFavoriteAdapter
        }

        // Todo: Get favorite movie data from database.
        viewModel.getListFavoriteMovie()
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
