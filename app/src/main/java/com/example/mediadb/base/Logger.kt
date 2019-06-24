package com.example.mediadb.base

import android.util.Log
import com.example.mediadb.BuildConfig

class Logger {
    companion object {
        fun d(tag: String, msg: String) {
            if (BuildConfig.DEBUG) {
                Log.d(tag, msg)
            }
        }

        fun e(tag: String, msg: String) {
            if(BuildConfig.DEBUG) {
                Log.e(tag, msg)
            }
        }
    }
}