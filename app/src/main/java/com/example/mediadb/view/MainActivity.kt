package com.example.mediadb.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.mediadb.R
import com.example.mediadb.base.view.BaseActivity
import com.example.mediadb.utils.Constants
import com.example.mediadb.view.moviefavorite.MovieFavoriteFragment
import com.example.mediadb.view.movielist.MovieListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    val movieListFragment: Fragment = MovieListFragment.newInstance()
    val movieFavoriteFragment: Fragment = MovieFavoriteFragment.newInstance()
    val fm = supportFragmentManager
    var activeFragment: Fragment = movieListFragment

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        main_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        fm.beginTransaction().add(R.id.container_fragment, movieFavoriteFragment, Constants.MOVIE_FAVORITE)
            .hide(movieFavoriteFragment).commit()
        fm.beginTransaction().add(R.id.container_fragment, movieListFragment, Constants.MOVIE_LIST).commit()
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_main
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                fm.beginTransaction().hide(activeFragment).show(movieListFragment).commit()
                activeFragment = movieListFragment
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorite -> {
                fm.beginTransaction().hide(activeFragment).show(movieFavoriteFragment).commit()
                activeFragment = movieFavoriteFragment
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

}
