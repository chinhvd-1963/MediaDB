package com.example.mediadb.data.repository

import android.app.Application
import com.example.mediadb.base.BaseRepository

class MovieRepositoryImp(application: Application) : BaseRepository(), MovieRepository {

    private val TAG = MovieRepositoryImp::class.java.simpleName
}