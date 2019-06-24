package com.example.mediadb.data.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.mediadb.base.BaseRepository
import com.example.mediadb.base.Logger
import com.example.mediadb.data.api.ApiBuilder
import com.example.mediadb.data.model.dataresponse.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieRepository(application: Application) : BaseRepository(), MovieRepositoryImp {

    private val TAG = MovieRepository::class.java.simpleName
    private val listMovieData = MutableLiveData<MutableList<Movie>>()

    override fun getListMovieData(hashMap: HashMap<String, String>) {

        disposables.add(ApiBuilder.getServiceApi().getMovieList(hashMap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                listMovieData.value = it.movies
                Logger.d(TAG, "getListMovieData() ${it.page} per ${it.movies[0].title}")
            }, {
                Logger.d(TAG, "showFailure")
            })
        )
    }

    fun showMoviesResponse(): MutableLiveData<MutableList<Movie>> {
        return listMovieData
    }
}