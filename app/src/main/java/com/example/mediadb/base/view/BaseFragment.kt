package com.example.mediadb.base.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner

abstract class BaseFragment : Fragment(), LifecycleOwner {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        onViewReady(view)
    }

    abstract fun initViewModel()

    abstract fun onViewReady(view: View)
}
