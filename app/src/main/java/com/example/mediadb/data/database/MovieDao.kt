package com.example.mediadb.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mediadb.data.model.dataresponse.Movie
import io.reactivex.Single

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    fun getListFavoriteMovie(): Single<MutableList<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteMovie(movie: Movie): Single<Long>

    @Query("DELETE FROM movie WHERE id = :id")
    fun deleteFavoriteMovie(id: String): Single<Int>
}