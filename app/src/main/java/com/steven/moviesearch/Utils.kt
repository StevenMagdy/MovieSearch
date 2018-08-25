package com.steven.moviesearch

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

	fun isNetworkAvailable(context: Context): Boolean {
		val connectivityManager = context.getSystemService(Context
				.CONNECTIVITY_SERVICE) as ConnectivityManager
		val networkInfo = connectivityManager.activeNetworkInfo
		return networkInfo?.isConnected == true
	}
}
