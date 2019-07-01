package com.example.mediadb.data.repository

import com.example.mediadb.data.model.dataresponse.ListMovieData
import com.example.mediadb.data.model.dataresponse.Movie
import io.reactivex.Completable
import io.reactivex.Single

interface MovieRepository {

    fun getListMovieData(option: HashMap<String, String>): Single<ListMovieData>

    fun getListFavoriteMovie(): Single<MutableList<Movie>>

    fun insertFavoriteMovie(movie: Movie): Completable

    fun deleteFavoriteMovie(id: Int): Completable

    fun isExistFavorite(id: Int): Single<Movie>
}