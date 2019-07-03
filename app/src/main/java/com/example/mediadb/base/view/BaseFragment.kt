package com.example.mediadb.base.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.mediadb.base.view.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.movie_favorite_fragment.*
import kotlinx.android.synthetic.main.movie_list_fragment.*

abstract class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        onViewReady(view)
    }

    abstract fun initViewModel()

    abstract fun onViewReady(view: View)

    fun setObserveEvent(viewModel: BaseViewModel) {

        viewModel.eventFailure.observe(viewLifecycleOwner, Observer {
            if (it.message != null) {
                Toast.makeText(activity, "${it.message}", Toast.LENGTH_SHORT).show()
            }
            swipe_refresh_list_movie?.isRefreshing = false
            swipe_refresh_favorite_movie?.isRefreshing = false
        })
    }
}
