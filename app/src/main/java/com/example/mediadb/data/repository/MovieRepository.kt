package com.example.mediadb.data.repository

import com.example.mediadb.data.model.dataresponse.ListMovieData
import com.example.mediadb.data.model.dataresponse.Movie
import io.reactivex.Single
import kotlin.collections.HashMap

interface MovieRepository {

    fun getListMovieData(option: HashMap<String, String>): Single<ListMovieData>

    suspend fun getListFavoriteMovie(): Single<MutableList<Movie>>

    suspend fun insertFavoriteMovie(movie: Movie)

    suspend fun deleteFavoriteMovie(id: String)
}