package com.steven.moviesearch;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class Utils {

	private static final String TAG = Utils.class.getName();

	public static final String BASE_URL = "https://api.themoviedb.org/3/";
	private static OkHttpClient okHttpClient;
	private static Retrofit retrofit;

	private Utils() {
	}

	public static synchronized OkHttpClient provideOkHttpClient() {
		if (okHttpClient == null) {
			okHttpClient = new OkHttpClient();
		}
		return okHttpClient;
	}

	public static synchronized Retrofit provideRetrofit(OkHttpClient okHttpClient) {
		if (retrofit == null) {
			retrofit = new Retrofit.Builder()
					.baseUrl(Utils.BASE_URL)
					.addConverterFactory(GsonConverterFactory.create())
					.client(okHttpClient)
					.build();
		}
		return retrofit;
	}

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context
				.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return networkInfo != null && networkInfo.isConnected();
	}
}
