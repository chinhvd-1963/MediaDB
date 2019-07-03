package com.example.mediadb.view.movielist

import androidx.lifecycle.MutableLiveData
import com.example.mediadb.base.view.viewmodel.BaseViewModel
import com.example.mediadb.base.view.viewmodel.SingleLiveEvent
import com.example.mediadb.data.model.dataresponse.Movie
import com.example.mediadb.data.repository.MovieRepository
import com.example.mediadb.utils.ApiUtils
import com.example.mediadb.utils.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException


class MovieListViewModel constructor(val movieRepository: MovieRepository) : BaseViewModel() {

    private val TAG = MovieListViewModel::class.java.simpleName

    companion object {
        const val DEFAULT_PAGE_NUMBER = 1
    }

    val listMovieData = SingleLiveEvent<MutableList<Movie>>()
    val movieItem = MutableLiveData<Movie>()
    val listFavoriteMovie = MutableLiveData<MutableList<Movie>>()
    val movieItemFavorite = MutableLiveData<Movie>()

    var movieListApi: MutableList<Movie> = ArrayList()

    val isFavorite = MutableLiveData<Boolean>().apply { value = false }
    val isEndlessLoading = MutableLiveData<Boolean>().apply { value = false }
    val isMovieListLoading = MutableLiveData<Boolean>().apply { value = false }
    val isHasNavigation = MutableLiveData<Boolean>().apply { value = true }
    val isListMovieInitialized = MutableLiveData<Boolean>().apply { value = false }

    val activeFragment = MutableLiveData<Int>().apply { value = Constants.MOVIE_LIST_FRAGMENT }
    val nextPage = MutableLiveData<Int>().apply { value = DEFAULT_PAGE_NUMBER }


    fun endlessLoading() {
        isEndlessLoading.value = true
        //page 1 is always loaded when entering the movie list screen.
        if (nextPage.value == DEFAULT_PAGE_NUMBER) {
            nextPage.value = nextPage.value?.plus(1)
        }
        getListMovieData(nextPage.value!!)
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
                        nextPage.value = nextPage.value?.plus(1)
                        isEndlessLoading.value = false
                    }
                    //Invisiable data loading.
                    isMovieListLoading.value = false
                }, {
                    when (it) {
                        is IOException -> {
                            //handle network error
                            isEndlessLoading.value = false
                            isMovieListLoading.value = false
                            showFailureThrowable(it)
                        }
                        is HttpException -> {
                            isEndlessLoading.value = false
                            isMovieListLoading.value = false
                            showFailureThrowable(it)
                        }
                        else -> {
                            isEndlessLoading.value = false
                            isMovieListLoading.value = false
                            showFailureThrowable(it)
                        }
                    }
                })
        )
    }

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

    fun saveListMovieInitialized() {
        isListMovieInitialized.value = true
    }
}
