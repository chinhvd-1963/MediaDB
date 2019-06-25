package com.example.mediadb.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.mediadb.R
import com.example.mediadb.base.Logger

class ApiUtils {
    companion object {
        const val API_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "3956f50a726a2f785334c24759b97dc6"
        const val PAGE = "page"
        const val API_KEY_PARAM = "api_key"
        const val BASE_IMAGE_URL = "http://image.tmdb.org/t/p/original"

        fun imageUrl(url: String): String {
            return BASE_IMAGE_URL.plus(url)
        }

        @JvmStatic
        @BindingAdapter("loadPoster")
        fun loadPosterUrl(view: AppCompatImageView, url: String) {
            Glide.with(view.context).load(imageUrl(url))
                .placeholder(R.drawable.background_movie).dontAnimate().into(view)
        }
    }
}