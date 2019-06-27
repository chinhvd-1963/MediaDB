package com.example.mediadb.view

import android.os.Bundle
import android.util.SparseArray
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.example.mediadb.R
import com.example.mediadb.base.view.BaseActivity
import com.example.mediadb.view.moviefavorite.MovieFavoriteFragment
import com.example.mediadb.view.movielist.MovieListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_fragment, MovieListFragment.newInstance())
                .commitAllowingStateLoss()
        }
        main_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_main
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container_fragment, MovieListFragment.newInstance())
                    .addToBackStack(MovieListFragment::class.java.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorite -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container_fragment, MovieFavoriteFragment.newInstance())
                    .addToBackStack(MovieFavoriteFragment::class.java.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

}
