package com.example.mediadb.data.api

import com.example.mediadb.data.model.dataresponse.ListMovieData
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiInterface {

    ///movie/popular?api_key={api_key}&page={page}
    @GET("movie/popular")
    fun getMovieList(@QueryMap option: HashMap<String, String>): Single<ListMovieData>
}