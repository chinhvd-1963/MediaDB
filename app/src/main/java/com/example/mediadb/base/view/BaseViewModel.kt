package com.example.mediadb.base.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {
    val eventFailure = MutableLiveData<Throwable>()
    val disposables = CompositeDisposable()

    override fun onCleared() {
        disposables.clear()
    }

    fun showFailureThrowable(throwable: Throwable) {
        eventFailure.value = throwable
    }
}