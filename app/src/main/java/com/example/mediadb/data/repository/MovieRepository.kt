package com.example.mediadb.data.repository

import com.example.mediadb.data.model.dataresponse.ListMovieData
import io.reactivex.Single
import kotlin.collections.HashMap

interface MovieRepository {

    fun getListMovieData(option: HashMap<String, String>): Single<ListMovieData>
}