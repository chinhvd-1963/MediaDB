package com.example.mediadb

import android.app.Application
import androidx.lifecycle.LifecycleObserver
import com.example.mediadb.di.module.repositoryModule
import com.example.mediadb.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application(), LifecycleObserver {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(repositoryModule, viewModelModule))
        }
    }
}