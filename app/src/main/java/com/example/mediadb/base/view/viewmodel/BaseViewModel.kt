package com.example.mediadb.base.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {
    val eventFailure = MutableLiveData<Throwable>()
    val eventDelete = MutableLiveData<Boolean>()
    val disposables = CompositeDisposable()

    override fun onCleared() {
        disposables.clear()
    }

    fun showFailureThrowable(throwable: Throwable) {
        eventFailure.value = throwable
    }

    fun showDeleteEvent(isSuccess: Boolean) {
        eventDelete.value = isSuccess
    }
}