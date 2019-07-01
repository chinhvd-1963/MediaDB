package com.example.mediadb.view.movielist

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessRecyclerOnScrollListener : RecyclerView.OnScrollListener() {

    //The total number of items in the dataset after the last load
    private var mPreviousTotal = 0

    //True if we are still waiting for the last set of data to load.
    private var mLoading = true

    private val visibleThreshold = 4

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = recyclerView.childCount
        val totalItemCount = recyclerView.layoutManager!!.itemCount
        val firstVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

        if (mLoading) {
            if (totalItemCount != mPreviousTotal) {
                mLoading = false
                mPreviousTotal = totalItemCount
            }
        }

        if (!mLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached
            onLoadMore()
            mLoading = true
        }
    }

    abstract fun onLoadMore()
}