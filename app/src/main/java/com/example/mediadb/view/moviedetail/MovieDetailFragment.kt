package com.example.mediadb.view.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.movie_detail_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun initViewModel() {
        setObserveEvent(viewModel)
        viewModel.movieItem.observe(viewLifecycleOwner, Observer {
            //update binding.viewModel when receive viewModel movieItem value.
            binding.viewModel = viewModel
            viewModel.isExistFavorite(it.id)
        })
        viewModel.movieItemFavorite.observe(viewLifecycleOwner, Observer {
            initBtnFloatingAction(it)
        })
        viewModel.eventDelete.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                Toast.makeText(
                    activity, activity?.resources?.getString(com.example.mediadb.R.string.toast_delete_success),
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.eventDelete.value = false
            }
        })
    }

    private fun initBtnFloatingAction(it: Movie?) {
        viewModel.isFavorite.value = it != null
    }

    override fun onViewReady(view: View) {
        initEvent()
    }

    private fun initEvent() {
        btn_favorite.setOnClickListener {
            binding.viewModel ?: return@setOnClickListener

            if (binding.viewModel?.movieItem?.value != null) {
                binding.viewModel?.isFavorite?.value = if (binding.viewModel?.isFavorite?.value!!) {
                    viewModel.deleteFavoriteMovie(binding.viewModel?.movieItem?.value!!.id)
                    false
                } else {
                    viewModel.insertFavoriteMovie(binding.viewModel?.movieItem?.value!!)
                    true
                }
            }
        }
        btn_back.setOnClickListener {
            var count = activity?.supportFragmentManager?.backStackEntryCount
            if (count == 0) {
                viewModel.isHasNavigation.value = true
                activity?.onBackPressed()
            } else {
                viewModel.isHasNavigation.value = true
                activity?.supportFragmentManager?.popBackStack()
            }
        }
    }


}
