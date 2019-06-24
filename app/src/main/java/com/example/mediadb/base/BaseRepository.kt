package com.example.mediadb.base

import io.reactivex.disposables.CompositeDisposable

open class BaseRepository {
    val disposables = CompositeDisposable()

    fun clearDisposables() {
        disposables.clear()
    }
}