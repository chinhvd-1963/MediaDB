package com.example.mediadb.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mediadb.data.model.dataresponse.Movie
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    fun getListFavoriteMovie(): Single<MutableList<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteMovie(movie: Movie): Completable

    @Query("DELETE FROM movie WHERE id = :id")
    fun deleteFavoriteMovie(id: Int): Completable

    @Query("SELECT * from movie WHERE id= :id")
    fun isExistFavorite(id: Int): Single<Movie>
}