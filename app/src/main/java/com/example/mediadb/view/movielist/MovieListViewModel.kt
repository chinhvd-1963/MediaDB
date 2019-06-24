package com.example.mediadb.view.movielist

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import com.example.mediadb.base.Logger
import com.example.mediadb.base.view.BaseViewModel
import com.example.mediadb.data.api.ApiBuilder
import com.example.mediadb.data.model.dataresponse.Movie
import com.example.mediadb.data.repository.MovieRepository
import com.example.mediadb.utils.ApiParams
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieListViewModel(@NonNull application: Application) : BaseViewModel(application) {

    private val TAG = MovieListViewModel::class.java.simpleName

    private companion object {
        const val DEFAULT_PAGE_NUMBER = 1
    }

    private val movieRepository = MovieRepository(application)
    private val listMovieData = MutableLiveData<MutableList<Movie>>()

    fun getListMovieData() {
        val option = HashMap<String, String>()
        option[ApiParams.API_KEY_PARAM] = ApiParams.API_KEY
        option[ApiParams.PAGE] = DEFAULT_PAGE_NUMBER.toString()
        disposables.add(
            ApiBuilder.getServiceApi().getMovieList(option)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listMovieData.value = it.movies
                    Logger.d(TAG, "getListMovieData() ${it.page} per ${it.movies[0].title}")
                }, {
                    Logger.d(TAG, "showFailure")
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun showListMovieData(): MutableLiveData<MutableList<Movie>> {
        return listMovieData
    }
}
