package com.steven.moviesearch.loaders

import android.content.Context
import android.support.v4.content.AsyncTaskLoader
import com.steven.moviesearch.EndPoints
import com.steven.moviesearch.R
import com.steven.moviesearch.Utils
import com.steven.moviesearch.models.ResultItem
import retrofit2.Call
import java.io.IOException

class DetailsLoader(context: Context, private val resultId: Int, private val resultMediaType: String) : AsyncTaskLoader<ResultItem>(context) {

	override fun loadInBackground(): ResultItem? {
		val okHttpClient = Utils.provideOkHttpClient()
		val retrofit = Utils.provideRetrofit(okHttpClient)
		val endPoints = retrofit.create(EndPoints::class.java)
		val resultItemCall: Call<ResultItem>
		if (resultMediaType == "movie") {
			resultItemCall = endPoints.getMovieDetails(resultId, context.getString(R.string.theMovieDB_api_key))
		} else {
			resultItemCall = endPoints.getTVDetails(resultId, context.getString(R.string
					.theMovieDB_api_key))
		}
		try {
			return resultItemCall.execute().body()
		} catch (e: IOException) {
			return null
		}

	}

	override fun onStartLoading() {
		forceLoad()
	}

	companion object {
		private val TAG = DetailsLoader::class.java.name
	}
}
