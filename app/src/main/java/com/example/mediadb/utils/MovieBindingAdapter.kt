package com.example.mediadb.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.mediadb.R

object MovieBindingAdapter {

    private fun imageUrl(url: String): String {
        return ApiUtils.BASE_IMAGE_URL.plus(url)
    }

    @JvmStatic
    @BindingAdapter("loadPoster")
    fun AppCompatImageView.loadPosterUrl(url: String?) {
        url?.let {
            Glide.with(this.context).load(imageUrl(it))
                .placeholder(R.drawable.movie_background).dontAnimate().into(this)
        }
    }
}