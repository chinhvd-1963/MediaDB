package com.example.mediadb.data.repository

import android.util.Log
import androidx.annotation.WorkerThread
import com.example.mediadb.data.api.ApiBuilder
import com.example.mediadb.data.database.MovieDao
import com.example.mediadb.data.model.dataresponse.ListMovieData
import com.example.mediadb.data.model.dataresponse.Movie
import io.reactivex.Single

class MovieRepositoryImp constructor(
    private val movieDao: MovieDao
) : MovieRepository {

    override fun getListMovieData(option: HashMap<String, String>): Single<ListMovieData> {
        return ApiBuilder.getServiceApi().getMovieList(option)
    }

    @WorkerThread
    override suspend fun getListFavoriteMovie(): Single<MutableList<Movie>> {
        return movieDao.getListFavoriteMovie()
    }

    @WorkerThread
    override suspend fun insertFavoriteMovie(movie: Movie) {
        Log.d("Chinh", "MovieRepositoryImp insertFavoriteMovie")
        movieDao.insertFavoriteMovie(movie)
    }

    @WorkerThread
    override suspend fun deleteFavoriteMovie(id: String) {

    }
}