package com.example.mediadb.base.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val disposables = CompositeDisposable()

    override fun onCleared() {
        disposables.clear()
    }
}