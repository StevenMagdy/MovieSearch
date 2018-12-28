package com.steven.moviesearch

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.os.AsyncTask
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

object Utils {

  private val TAG = Utils::class.java.name

  private const val BASE_URL = "https://api.themoviedb.org/3/"
  private lateinit var okHttpClient: OkHttpClient
  private lateinit var retrofit: Retrofit

  @Synchronized
  fun provideOkHttpClient(): OkHttpClient {
    if (!::okHttpClient.isInitialized) {
      okHttpClient = OkHttpClient()
    }
    return okHttpClient
  }

  @Synchronized
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    if (!::retrofit.isInitialized) {
      retrofit = Retrofit.Builder()
          .baseUrl(Utils.BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .client(okHttpClient)
          .build()
    }
    return retrofit
  }

  fun isNetworkAvailable(application: Application, handler: (Boolean) -> (Unit)) {
    val connectivityManager = application.getSystemService(Context
        .CONNECTIVITY_SERVICE) as? ConnectivityManager
    val networkInfo = connectivityManager?.activeNetworkInfo
    if (networkInfo?.isConnected != true) handler(false)
    else InternetCheck(handler)
  }

  class InternetCheck(private val handler: (Boolean) -> (Unit)) : AsyncTask<Void, Void, Boolean>() {
    init {
      execute()
    }

    override fun doInBackground(vararg voids: Void): Boolean {
      try {
        val sock = Socket()
        sock.connect(InetSocketAddress("8.8.8.8", 53), 1500)
        sock.close()
        return true
      } catch (e: IOException) {
        return false
      }
    }

    override fun onPostExecute(internet: Boolean) {
      handler(internet)
    }
  }
}
