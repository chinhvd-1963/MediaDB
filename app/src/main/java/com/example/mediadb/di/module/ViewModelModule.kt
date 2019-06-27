package com.example.mediadb.di.module

import androidx.databinding.library.baseAdapters.BR.viewModel
import com.example.mediadb.view.movielist.MovieListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MovieListViewModel(get()) }
}