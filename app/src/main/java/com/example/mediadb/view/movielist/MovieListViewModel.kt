package com.example.mediadb.view.movielist

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mediadb.base.view.BaseViewModel
import com.example.mediadb.data.model.dataresponse.Movie
import com.example.mediadb.data.repository.MovieRepository
import com.example.mediadb.utils.ApiUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch


class MovieListViewModel constructor(val movieRepository: MovieRepository) : BaseViewModel() {

    private val TAG = MovieListViewModel::class.java.simpleName

    companion object {
        const val DEFAULT_PAGE_NUMBER = 1
    }

    private val listMovieData = MutableLiveData<MutableList<Movie>>()
    val movieItem = MutableLiveData<Movie>()
    private val listFavoriteMovie = MutableLiveData<MutableList<Movie>>()

    fun getListMovieData() {
        val option = HashMap<String, String>()
        option[ApiUtils.API_KEY_PARAM] = ApiUtils.API_KEY
        option[ApiUtils.PAGE] = DEFAULT_PAGE_NUMBER.toString()
        disposables.add(
            movieRepository.getListMovieData(option).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    listMovieData.value = it.movies
                }, {
                    //Todo: implement show notification later.
                })
        )
    }

    @SuppressLint("CheckResult")
    fun getListFavoriteMovie() {
            movieRepository.getListFavoriteMovie().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    listFavoriteMovie.value = it
                }, {
                    //Todo: implement show notification later.
                })
    }

    fun insertFavoriteMovie(movie: Movie) {
        viewModelScope.launch {
            try {
                movieRepository.insertFavoriteMovie(movie)
            } catch (e: Exception) {

            }
        }
    }

    fun setSelectedMovie(movie: Movie) {
        this.movieItem.value = movie
    }

    fun showListMovieData(): MutableLiveData<MutableList<Movie>> {
        return listMovieData
    }

    fun showListFavoriteMovie(): MutableLiveData<MutableList<Movie>> {
        return listFavoriteMovie
    }
}
