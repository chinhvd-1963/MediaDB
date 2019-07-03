package com.example.mediadb.view

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.mediadb.R
import com.example.mediadb.base.view.BaseActivity
import com.example.mediadb.databinding.ActivityMainBinding
import com.example.mediadb.utils.Constants
import com.example.mediadb.view.moviefavorite.MovieFavoriteFragment
import com.example.mediadb.view.movielist.MovieListFragment
import com.example.mediadb.view.movielist.MovieListViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity() {

    private val viewModel: MovieListViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    val movieListFragment: Fragment = MovieListFragment.newInstance()
    val movieFavoriteFragment: Fragment = MovieFavoriteFragment.newInstance()
    val fm = supportFragmentManager
    var activeFragment: Fragment = movieListFragment

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        main_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        fm.beginTransaction().add(R.id.container_fragment, movieFavoriteFragment, Constants.MOVIE_FAVORITE)
            .hide(movieFavoriteFragment).commit()
        fm.beginTransaction().add(R.id.container_fragment, movieListFragment, Constants.MOVIE_LIST).commit()

        initViewModel()
    }

    private fun initViewModel() {
        viewModel.isHasNavigation.observe(this, Observer {
            binding.viewModel = viewModel
        })
        viewModel.activeFragment.observe(this, Observer {
            when (it) {
                Constants.MOVIE_LIST_FRAGMENT -> {
                    fm.beginTransaction().hide(activeFragment).show(movieListFragment).commit()
                    activeFragment = movieListFragment
                }
                Constants.MOVIE_FAVORITE_FRAGMENT -> {
                    fm.beginTransaction().hide(activeFragment).show(movieFavoriteFragment).commit()
                    activeFragment = movieFavoriteFragment
                }
            }
        })
    }

    override fun layoutDatabinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                viewModel.activeFragment.value = Constants.MOVIE_LIST_FRAGMENT
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorite -> {
                viewModel.activeFragment.value = Constants.MOVIE_FAVORITE_FRAGMENT
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount

        if (count == 0) {
            super.onBackPressed()
            viewModel.isHasNavigation.value = true
        } else {
            viewModel.isHasNavigation.value = true
            supportFragmentManager.popBackStack()
        }
    }
}
