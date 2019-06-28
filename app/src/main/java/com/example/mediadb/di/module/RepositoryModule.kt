package com.example.mediadb.di.module

import android.content.Context
import androidx.room.Room
import com.example.mediadb.data.database.AppDatabase
import com.example.mediadb.data.repository.MovieRepository
import com.example.mediadb.data.repository.MovieRepositoryImp
import com.example.mediadb.utils.DataBaseConstants
import com.google.gson.Gson
import org.koin.dsl.module

val repositoryModule = module {
    single { createDatabaseName() }
    single { createAppDatabase(get(), get()) }
    single { createMovieDao(get()) }
    single { Gson() }
    single<MovieRepository> { MovieRepositoryImp(get()) }
}

fun createDatabaseName() = DataBaseConstants.DATABASE_NAME

fun createAppDatabase(dbName: String, context: Context) =
    Room.databaseBuilder(context, AppDatabase::class.java, dbName).build()

fun createMovieDao(appDatabase: AppDatabase) = appDatabase.movieDao()