package com.example.mediadb.data.api

import com.example.mediadb.data.model.Movie
import com.example.mediadb.data.model.dataresponse.ListMovieData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("/movie/popular?api_key={api_key}&page={page}")
    fun getMovieList(@Path("api_key") apiKey:String, @Path("page") page: Int): Observable<ListMovieData>
}