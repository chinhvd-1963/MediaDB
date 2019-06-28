package com.example.mediadb.data.repository

import com.example.mediadb.data.api.ApiBuilder
import com.example.mediadb.data.database.MovieDao
import com.example.mediadb.data.model.dataresponse.ListMovieData
import com.example.mediadb.data.model.dataresponse.Movie
import io.reactivex.Completable
import io.reactivex.Single

class MovieRepositoryImp constructor(
    private val movieDao: MovieDao
) : MovieRepository {

    override fun getListMovieData(option: HashMap<String, String>): Single<ListMovieData> {
        return ApiBuilder.getServiceApi().getMovieList(option)
    }

    override fun getListFavoriteMovie(): Single<MutableList<Movie>> {
        return movieDao.getListFavoriteMovie()
    }

    override fun insertFavoriteMovie(movie: Movie): Completable {
        return movieDao.insertFavoriteMovie(movie)
    }

    override fun deleteFavoriteMovie(id: Int): Completable {
        return movieDao.deleteFavoriteMovie(id)
    }
}