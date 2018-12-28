package com.steven.moviesearch.detalis

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.steven.moviesearch.EndPoints
import com.steven.moviesearch.R
import com.steven.moviesearch.Utils
import com.steven.moviesearch.models.ResultItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsViewModel(application: Application) : AndroidViewModel(application) {
  var resultId = -1
  var resultMediaType: String = ""

  companion object {
    val TAG: String = DetailsViewModel::class.java.name
  }

  private val _resultItem = MutableLiveData<ResultItem>()

  val resultItem: LiveData<ResultItem>
    get() = _resultItem

  override fun onCleared() {
    super.onCleared()
    Log.i(TAG, "onCleared")
  }

  fun reload() {
    val okHttpClient = Utils.provideOkHttpClient()
    val retrofit = Utils.provideRetrofit(okHttpClient)
    val endPoints = retrofit.create(EndPoints::class.java)
    val resultItemCall: Call<ResultItem>
    if (resultMediaType == "movie") {
      resultItemCall = endPoints.getMovieDetails(resultId, getApplication<Application>().getString(R.string.theMovieDB_api_key))
    } else {
      resultItemCall = endPoints.getTVDetails(resultId, getApplication<Application>().getString(R.string
          .theMovieDB_api_key))
    }
    resultItemCall.enqueue(object : Callback<ResultItem> {
      override fun onFailure(call: Call<ResultItem>, t: Throwable) {
        _resultItem.value = null
      }

      override fun onResponse(call: Call<ResultItem>, response: Response<ResultItem>) {
        _resultItem.value = response.body()
      }
    })
  }
}
