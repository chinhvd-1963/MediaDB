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
}