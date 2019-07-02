package com.example.mediadb.view.movielist

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.example.mediadb.base.view.BaseViewModel
import com.example.mediadb.data.model.dataresponse.Movie
import com.example.mediadb.data.repository.MovieRepository
import com.example.mediadb.utils.ApiUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MovieListViewModel constructor(val movieRepository: MovieRepository) : BaseViewModel() {

    private val TAG = MovieListViewModel::class.java.simpleName

    companion object {
        const val DEFAULT_PAGE_NUMBER = 1
    }

    val listMovieData = MutableLiveData<MutableList<Movie>>()
    val movieItem = MutableLiveData<Movie>()
    val listFavoriteMovie = MutableLiveData<MutableList<Movie>>()
    val movieItemFavorite = MutableLiveData<Movie>()

    var movieListApi: MutableList<Movie> = ArrayList()

    val isFavorite = MutableLiveData<Boolean>().apply { value = false }
    val isEndlessLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading = MutableLiveData<Boolean>().apply { value = false }

    private var nextPage = DEFAULT_PAGE_NUMBER

    fun endlessLoading() {
        isEndlessLoading.value = true
        //page 1 is always loaded when entering the movie list screen.
        if (nextPage == DEFAULT_PAGE_NUMBER) {
            nextPage++
        }
        getListMovieData(nextPage)
    }

    fun getListMovieData(page: Int) {
        val option = HashMap<String, String>()
        option[ApiUtils.API_KEY_PARAM] = ApiUtils.API_KEY
        option[ApiUtils.PAGE] = page.toString()
        disposables.add(
            movieRepository.getListMovieData(option).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    listMovieData.value = it.movies

                    //Invisiable endless loading.
                    if (isEndlessLoading.value == true) {
                            nextPage++
                            isEndlessLoading.value = false
                    }
                    //Invisiable data loading.
                    isLoading.value = false
                }, {
                    //Invisiable endless loading.
                    isEndlessLoading.value = false
                    //Invisiable data loading.
                    isLoading.value = false
                    showFailureThrowable(it)
                })
        )
    }

    @SuppressLint("CheckResult")
    fun getListFavoriteMovie() {
        disposables.add(
            movieRepository.getListFavoriteMovie().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    listFavoriteMovie.value = it
                }, {
                    showFailureThrowable(it)
                })
        )
    }

    fun insertFavoriteMovie(movie: Movie) {
        disposables.add(
            movieRepository.insertFavoriteMovie(movie).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    //Do nothing in here
                }, {
                    showFailureThrowable(it)
                })
        )
    }

    fun deleteFavoriteMovie(id: Int) {
        disposables.add(
            movieRepository.deleteFavoriteMovie(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    showDeleteEvent(true)
                }, {
                    showFailureThrowable(it)
                })
        )
    }

    fun isExistFavorite(id: Int) {
        disposables.add(
            movieRepository.isExistFavorite(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    movieItemFavorite.value = it
                }, {
                    movieItemFavorite.value = null
                })
        )

    }
}
