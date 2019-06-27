package com.example.mediadb.data.repository

import com.example.mediadb.data.api.ApiBuilder
import com.example.mediadb.data.model.dataresponse.ListMovieData
import io.reactivex.Single

class MovieRepositoryImp : MovieRepository {

    override fun getListMovieData(option: HashMap<String, String>): Single<ListMovieData> {
        return ApiBuilder.getServiceApi().getMovieList(option)
    }
}