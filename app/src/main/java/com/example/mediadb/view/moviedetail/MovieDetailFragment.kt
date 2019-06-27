package com.example.mediadb.view.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mediadb.R
import com.example.mediadb.base.view.BaseFragment
import com.example.mediadb.databinding.MovieDetailFragmentBinding
import com.example.mediadb.view.movielist.MovieListViewModel
import kotlinx.android.synthetic.main.movie_detail_fragment.*

class MovieDetailFragment : BaseFragment() {

    companion object {
        fun newInstance() = MovieDetailFragment()
    }

    private lateinit var viewModel: MovieListViewModel
    private lateinit var binding: MovieDetailFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.movie_detail_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun initViewModel() {
        if (activity != null) {
            viewModel = ViewModelProviders.of(activity!!).get(MovieListViewModel::class.java)

            viewModel.movieItem.observe(viewLifecycleOwner, Observer {
                binding.movieItem = it
            })
        }
    }

    override fun onViewReady(view: View) {
        // Init Floating action button.
        initEvent()
    }

    private fun initEvent() {
        btn_favorite.setOnClickListener {
            Toast.makeText(activity, "${binding.movieItem?.title}", Toast.LENGTH_SHORT).show()
        }
        btn_back.setOnClickListener {
            activity?.onBackPressed()
        }
    }

}
