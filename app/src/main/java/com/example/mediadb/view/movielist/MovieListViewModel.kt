package com.example.mediadb.view.movielist

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

    private val listMovieData = MutableLiveData<MutableList<Movie>>()
    val movieItem = MutableLiveData<Movie>()

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

    fun setSelectedMovie(movie: Movie) {
        this.movieItem.value = movie
    }

    fun showListMovieData(): MutableLiveData<MutableList<Movie>> {
        return listMovieData
    }
}
