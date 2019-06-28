package com.example.mediadb.view.movielist

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.example.mediadb.base.view.BaseViewModel
import com.example.mediadb.data.model.dataresponse.Movie
import com.example.mediadb.data.repository.MovieRepository
import com.example.mediadb.utils.ApiUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MovieListViewModel constructor(val movieRepository: MovieRepository) : BaseViewModel() {

    private val TAG = MovieListViewModel::class.java.simpleName

    companion object {
        const val DEFAULT_PAGE_NUMBER = 1
    }

    val listMovieData = MutableLiveData<MutableList<Movie>>()
    val movieItem = MutableLiveData<Movie>()
    val listFavoriteMovie = MutableLiveData<MutableList<Movie>>()

    fun getListMovieData() {
        val option = HashMap<String, String>()
        option[ApiUtils.API_KEY_PARAM] = ApiUtils.API_KEY
        option[ApiUtils.PAGE] = DEFAULT_PAGE_NUMBER.toString()
        disposables.add(
            movieRepository.getListMovieData(option).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    listMovieData.value = it.movies
                }, {
                    showFailureThrowable(it)
                })
        )
    }

    @SuppressLint("CheckResult")
    fun getListFavoriteMovie() {
        disposables.add(
            movieRepository.getListFavoriteMovie().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    listFavoriteMovie.value = it
                }, {
                    showFailureThrowable(it)
                })
        )
    }

    fun insertFavoriteMovie(movie: Movie) {
        disposables.add(
            movieRepository.insertFavoriteMovie(movie).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    //Do nothing in here
                }, {
                    showFailureThrowable(it)
                })
        )
    }

    fun setSelectedMovie(movie: Movie) {
        this.movieItem.value = movie
    }
}
