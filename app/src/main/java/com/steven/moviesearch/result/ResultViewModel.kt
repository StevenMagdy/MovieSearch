package com.steven.moviesearch.result

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.steven.moviesearch.EndPoints
import com.steven.moviesearch.R
import com.steven.moviesearch.Utils
import com.steven.moviesearch.models.ResultItem
import com.steven.moviesearch.models.SearchResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultViewModel(application: Application) : AndroidViewModel(application) {

  var searchQuery: String? = null
  private val _result = MutableLiveData<List<ResultItem>>()
  val result: LiveData<List<ResultItem>>
    get() {
      return _result
    }

  fun reLoad() {
    if (searchQuery.isNullOrBlank()){
      _result.value = null
      return
    }
    val okHttpClient = Utils.provideOkHttpClient()
    val retrofit = Utils.provideRetrofit(okHttpClient)
    val endPoints = retrofit.create(EndPoints::class.java)
    val searchResultCall = endPoints.getSearchResult(getApplication<Application>().getString(R.string.theMovieDB_api_key),
        searchQuery!!)

    searchResultCall.enqueue(object : Callback<SearchResult> {
      override fun onFailure(call: Call<SearchResult>, t: Throwable) {
        _result.value = null
      }

      override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
        _result.value = response.body()?.results?.filter {
          return@filter when (it.mediaType?.toLowerCase()) {
            "tv", "movie" -> true
            else -> false
          }
        }
      }
    })
  }
}
