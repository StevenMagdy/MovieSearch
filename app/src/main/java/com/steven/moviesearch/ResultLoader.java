package com.steven.moviesearch;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.steven.moviesearch.models.ResultItem;
import com.steven.moviesearch.models.SearchResult;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;

public class ResultLoader extends AsyncTaskLoader<List<ResultItem>> {

	private static final String TAG = ResultLoader.class.getName();

	private String searchQuery;
	private Context context;

	public ResultLoader(Context context, String searchQuery) {
		super(context);
		this.context = context;
		this.searchQuery = searchQuery;
	}

	@Override
	protected void onStartLoading() {
		forceLoad();
	}

	@Override
	public List<ResultItem> loadInBackground() {
		OkHttpClient okHttpClient = Utils.provideOkHttpClient();
		Retrofit retrofit = Utils.provideRetrofit(okHttpClient);
		EndPoints endPoints = retrofit.create(EndPoints.class);
		Call<SearchResult> searchResultCall =
				endPoints.getSearchResult(context.getString(R.string.theMovieDB_api_key),
						searchQuery);
		try {
			return searchResultCall.execute().body().getResults();
		} catch (IOException e) {
			return null;
		}
	}
}
