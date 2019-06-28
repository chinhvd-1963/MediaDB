package com.example.mediadb.view.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.mediadb.R
import com.example.mediadb.base.view.BaseFragment
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.movie_detail_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun initViewModel() {
        viewModel.movieItem.observe(viewLifecycleOwner, Observer {
            binding.movieItem = it
        })
    }

    override fun onViewReady(view: View) {
        // Init Floating action button.
        initEvent()
    }

    private fun initEvent() {
        btn_favorite.setOnClickListener {
            if (binding.movieItem != null) {
                viewModel.insertFavoriteMovie(binding.movieItem!!)
            }
        }
        btn_back.setOnClickListener {
            activity?.onBackPressed()
        }
    }

}
