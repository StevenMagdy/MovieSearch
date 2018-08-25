package com.steven.moviesearch.loaders

import android.content.Context
import android.support.v4.content.AsyncTaskLoader
import android.util.Log
import com.steven.moviesearch.EndPoints
import com.steven.moviesearch.R
import com.steven.moviesearch.Utils
import com.steven.moviesearch.models.ResultItem

class ResultLoader(context: Context, private val searchQuery: String) : AsyncTaskLoader<List<ResultItem>?>(context) {

	override fun onStartLoading() {
		forceLoad()
	}

	override fun loadInBackground(): List<ResultItem>? {
		val okHttpClient = Utils.provideOkHttpClient()
		val retrofit = Utils.provideRetrofit(okHttpClient)
		val endPoints = retrofit.create(EndPoints::class.java)
		val searchResultCall = endPoints.getSearchResult(context.getString(R.string.theMovieDB_api_key),
				searchQuery)
		try {
			val resultItems = searchResultCall.execute().body()?.results?.toMutableList()
					?: return null

			val iterator = resultItems.iterator()
			while (iterator.hasNext()) {
				val item = iterator.next()
				if (item.mediaType != "tv" && item.mediaType != "movie") {
					iterator.remove()
				}
			}
			return resultItems
		} catch (e: Exception) {
			Log.e("tag", "error", e)
			return null
		}
	}

	companion object {
		private val TAG = ResultLoader::class.java.name
	}
}
