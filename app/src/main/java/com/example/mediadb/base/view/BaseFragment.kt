package com.example.mediadb.base.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.mediadb.utils.Constants

abstract class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        onViewReady(view)
    }

    abstract fun initViewModel()

    abstract fun onViewReady(view: View)

    fun setObserveEvent(viewModel: BaseViewModel) {

        viewModel.eventFailure.observe(this, Observer {
            if (it.message != null) {
                Toast.makeText(activity, "${it.message}", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.eventDelete.observe(this, Observer {
            Toast.makeText(activity, Constants.TOAST_DELETE_SUCCESS, Toast.LENGTH_SHORT).show()
        })
    }
}
