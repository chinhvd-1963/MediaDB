package com.example.mediadb.di.module

import com.example.mediadb.data.repository.MovieRepository
import com.example.mediadb.data.repository.MovieRepositoryImp
import com.google.gson.Gson
import org.koin.dsl.module

val repositoryModule = module {
    single { Gson() }
    single<MovieRepository> { MovieRepositoryImp(get()) }
}