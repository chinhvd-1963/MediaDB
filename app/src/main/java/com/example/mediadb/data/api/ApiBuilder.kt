package com.example.mediadb.data.api

import com.example.mediadb.utils.ApiParams
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiBuilder {
    companion object {
        fun getServiceApi(): ApiInterface {
            val retrofit = Retrofit.Builder().baseUrl(ApiParams.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}