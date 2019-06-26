package com.example.mediadb.data.model

data class MovieEntity(
    var id: Int,
    var video: Boolean,
    var vote_average: Double,
    var title: String,
    var popularity: Double,
    var poster_path: String,
    var original_language: String,
    var original_title: String,
    var adult: Boolean,
    var overview: String,
    var release_date: String
)