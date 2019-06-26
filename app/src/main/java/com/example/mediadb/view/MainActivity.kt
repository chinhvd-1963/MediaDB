package com.example.mediadb.view

import android.os.Bundle
import com.example.mediadb.R
import com.example.mediadb.base.view.BaseActivity
import com.example.mediadb.view.movielist.MovieListFragment

class MainActivity : BaseActivity() {

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MovieListFragment.newInstance())
                .commitAllowingStateLoss()
        }
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_main
    }
}
