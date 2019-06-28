package com.example.mediadb.view.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.mediadb.R
import com.example.mediadb.base.view.BaseFragment
import com.example.mediadb.data.model.dataresponse.Movie
import com.example.mediadb.databinding.MovieDetailFragmentBinding
import com.example.mediadb.view.movielist.MovieListViewModel
import kotlinx.android.synthetic.main.movie_detail_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MovieDetailFragment : BaseFragment() {

    companion object {
        fun newInstance() = MovieDetailFragment()
    }

    private val viewModel: MovieListViewModel by sharedViewModel()
    private lateinit var binding: MovieDetailFragmentBinding
    private var isFavorite = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.movie_detail_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun initViewModel() {
        setObserveEvent(viewModel)
        viewModel.movieItem.observe(viewLifecycleOwner, Observer {
            binding.movieItem = it
            viewModel.isExistFavorite(it.id)
        })
        viewModel.movieItemFavorite.observe(viewLifecycleOwner, Observer {
            initBtnFloatingAction(it)
        })
    }

    private fun initBtnFloatingAction(it: Movie?) {
        if (it == null) {
            isFavorite = false
            btn_favorite.setImageResource(R.drawable.ic_favorite_white_24dp)
        } else {
            isFavorite = true
            btn_favorite.setImageResource(R.drawable.ic_favorite_red_24dp)
        }
    }

    override fun onViewReady(view: View) {
        initEvent()
    }

    private fun initEvent() {
        btn_favorite.setOnClickListener {
            binding.movieItem ?: return@setOnClickListener
            isFavorite = if (isFavorite) {
                viewModel.deleteFavoriteMovie(binding.movieItem!!.id)
                btn_favorite.setImageResource(R.drawable.ic_favorite_white_24dp)
                false
            } else {
                viewModel.insertFavoriteMovie(binding.movieItem!!)
                btn_favorite.setImageResource(R.drawable.ic_favorite_red_24dp)
                true
            }
        }
        btn_back.setOnClickListener {
            activity?.onBackPressed()
        }
    }

}
