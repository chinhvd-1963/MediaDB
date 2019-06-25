package com.example.mediadb.data.model.dataresponse


import com.google.gson.annotations.SerializedName

data class ListMovieData(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val movies: MutableList<Movie>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)